package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.Booking;
import basridrm.travelapp.data.entity.Room;
import basridrm.travelapp.data.entity.RoomStatus;
import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.data.repository.BookingRepository;
import basridrm.travelapp.data.repository.HotelRepository;
import basridrm.travelapp.data.repository.RoomRepository;
import basridrm.travelapp.dto.binding.BookingBindingModel;
import basridrm.travelapp.service.BookingService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomServiceImpl roomService;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomServiceImpl roomService, HotelRepository hotelRepository, RoomRepository roomRepository,
                              UserServiceImpl userService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.roomService = roomService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookingBindingModel> findAll() {
        return this.bookingRepository.findAll()
                .stream()
                .map(booking -> this.modelMapper.map(booking, BookingBindingModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookingBindingModel findById(Long bookingId) throws NotFoundException {
        return this.bookingRepository.findById(bookingId)
                .map(booking -> this.modelMapper.map(booking, BookingBindingModel.class))
                .orElseThrow(() -> new NotFoundException("Cannot find booking with the given id!"));
    }

    @Override
    public List<BookingBindingModel> findAllByUser(String username) {
        return this.bookingRepository.findAllByUserUsername(username)
                .stream()
                .filter(Booking::isFinished)
                .map(booking -> modelMapper.map(booking, BookingBindingModel.class))
                .collect(Collectors.toList());
    }

    public List<Room> findAvailableRooms(Integer guestNumber, Long hotelId, LocalDate checkIn, LocalDate checkOut) {
        return filterAvailableRooms(roomService.findAllByHotelId(hotelId).stream(), guestNumber, checkIn, checkOut)
                .collect(Collectors.toList());
    }

    public Stream<Room> filterAvailableRooms(Stream<Room> roomStream, Integer guestNumber, LocalDate checkIn, LocalDate checkOut) {
        return roomStream.filter(room -> filterStatus(room) || filterBookings(room, checkIn, checkOut))
                .filter(room -> filterOccupancy(room, guestNumber));
    }

    public boolean filterOccupancy(Room room, Integer guestNumber) {
        return guestNumber <= room.getMaxOccupancy();
    }

    public boolean filterStatus(Room room) {
        return room.getRoomStatus().equals(RoomStatus.AVAILABLE);
    }

    public boolean filterBookings(Room room, LocalDate checkIn, LocalDate checkOut){
        return room.getBookings()
                .stream()
                .filter(Booking::isFinished)
                .allMatch(booking -> validateCheckIn(booking, checkIn) && validateCheckOut(booking, checkOut));
    }

    public boolean validateCheckIn(Booking booking, LocalDate checkIn) {
        return !(checkIn.isAfter(booking.getCheckIn()) && checkIn.isBefore(booking.getCheckOut()));
    }

    public boolean validateCheckOut(Booking booking, LocalDate checkOut) {
        return !(checkOut.isAfter(booking.getCheckIn()) && checkOut.isBefore(booking.getCheckOut()));
    }

    public Long createBooking(Principal principal, BookingBindingModel model) {
        Booking booking = this.modelMapper.map(model, Booking.class);
        booking.setTotalPrice(BigDecimal.valueOf(1));
        booking.setUser(this.modelMapper.map(this.userService.loadUserByUsername(principal.getName()), User.class));
        return bookingRepository.save(booking).getId();
    }

    public void updateBookingWithRoom(Long bookingId, Long roomId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        Room room = roomService.findById(roomId);
        booking.setRoom(room);
        BigDecimal roomPricePerNight = room.getPricePerNight();
        BigDecimal daysBetween = BigDecimal.valueOf(ChronoUnit.DAYS.between(booking.getCheckIn(),
                booking.getCheckOut()));
        BigDecimal totalBookingPrice = daysBetween.multiply(roomPricePerNight);
        booking.setTotalPrice(totalBookingPrice);
        roomService.bookRoomById(roomId);
        booking.setFinished(true);
        Booking finishedBooking = bookingRepository.saveAndFlush(booking);
        roomService.updateRoomBooking(roomId, finishedBooking);
    }
}