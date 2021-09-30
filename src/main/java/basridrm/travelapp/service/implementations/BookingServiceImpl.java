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

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomServiceImpl roomService;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomServiceImpl roomService, HotelRepository hotelRepository, RoomRepository roomRepository, UserServiceImpl userService, ModelMapper modelMapper) {
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
        return this.bookingRepository.findAllByUser_Username(username)
                .stream()
                .filter(Booking::isFinished)
                .map(booking -> modelMapper.map(booking, BookingBindingModel.class))
                .collect(Collectors.toList());
    }

    public List<Room> findAvailableRooms(Integer guestNumber, Long hotelId, LocalDate checkIn) { //TODO: add binding model
        List<Room> rooms = roomService.findAllByHotelId(hotelId)
                .stream()
                .filter(room -> room.getRoomStatus().equals(RoomStatus.AVAILABLE) || room.getBookings().stream().filter(Booking::isFinished).allMatch(booking -> booking.getCheckOut().isBefore(checkIn)))
                .filter(room -> guestNumber <= room.getMaxOccupancy())
                .collect(Collectors.toList());

        return rooms;
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
        roomService.bookRoomById(roomId, booking);
        booking.setFinished(true);
        Booking finishedBooking = bookingRepository.saveAndFlush(booking);
        roomService.updateRoomBooking(roomId, finishedBooking);
    }
}