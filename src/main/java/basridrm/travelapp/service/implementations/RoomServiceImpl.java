package basridrm.travelapp.service.implementations;

import basridrm.travelapp.data.entity.Booking;
import basridrm.travelapp.data.entity.Room;
import basridrm.travelapp.data.entity.RoomStatus;
import basridrm.travelapp.data.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void bookRoomById(Long roomId, Booking booking) {
        Room room = roomRepository.getById(roomId);
        room.setRoomStatus(RoomStatus.NOT_AVAILABLE);
        roomRepository.save(room);
    }

    public void updateRoomBooking(Long roomId, Booking booking){
        Room room = roomRepository.getById(roomId);
        List<Booking> bookings = room.getBookings();
        bookings.add(booking);
        room.setBookings(bookings);
        roomRepository.save(room);
    }

    public List<Room> findAllByHotelId(Long hotelId) {
        return roomRepository.findAllByHotelId(hotelId);
    }

    public Room findById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("No room with given id"));
    }
}
