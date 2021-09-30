package basridrm.travelapp.service;

import basridrm.travelapp.dto.binding.BookingBindingModel;
import javassist.NotFoundException;

import java.util.List;

public interface BookingService {
    List<BookingBindingModel> findAll();
    BookingBindingModel findById(Long bookingId) throws NotFoundException;
    List<BookingBindingModel> findAllByUser(String username);
}
