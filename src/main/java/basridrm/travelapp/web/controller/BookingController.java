package basridrm.travelapp.web.controller;

import basridrm.travelapp.service.implementations.BookingServiceImpl;
import basridrm.travelapp.service.implementations.HotelServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;
    private final HotelServiceImpl hotelService;
    private final ModelMapper modelMapper;

    public BookingController(BookingServiceImpl bookingService, HotelServiceImpl hotelService, ModelMapper modelMapper) {
        this.bookingService = bookingService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getBookingPage() {


        return "/booking/bookings-index";
    }
}
