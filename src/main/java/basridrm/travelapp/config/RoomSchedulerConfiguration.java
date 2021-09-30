package basridrm.travelapp.config;

import basridrm.travelapp.data.entity.Booking;
import basridrm.travelapp.data.entity.RoomStatus;
import basridrm.travelapp.data.repository.BookingRepository;
import basridrm.travelapp.data.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class RoomSchedulerConfiguration implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public RoomSchedulerConfiguration(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        LocalDate dateTime = LocalDate.now();

        List<Booking> bookings = bookingRepository
                .findAll()
                .stream()
                .filter(booking -> booking.getCheckOut().isBefore(dateTime))
                .collect(Collectors.toList());

        bookings.stream().map(booking -> {
            Long bookedRoomId = booking.getRoom().getId();
            roomRepository.findById(bookedRoomId).ifPresent(room -> {
                room.setRoomStatus(RoomStatus.AVAILABLE);
                roomRepository.saveAndFlush(room);
            });
            return booking;
        }).forEach(updatedBooking -> bookingRepository.deleteById(updatedBooking.getId()));
    }
}
