package basridrm.travelapp.web.controller;

import basridrm.travelapp.data.entity.Room;
import basridrm.travelapp.dto.binding.BookingBindingModel;
import basridrm.travelapp.dto.view.HotelDetailsViewModel;
import basridrm.travelapp.service.implementations.BookingServiceImpl;
import basridrm.travelapp.service.implementations.HotelServiceImpl;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/hotel/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String getBookingPage(@ModelAttribute BookingBindingModel bookingBindingModel,
                                 @PathVariable("id") Long hotelId, Model model) throws NotFoundException {
        HotelDetailsViewModel hotel = this.modelMapper.
                map(this.hotelService.findById(hotelId), HotelDetailsViewModel.class);

        model.addAttribute("hotelBookingForm", bookingBindingModel);
        model.addAttribute("hotel", hotel);

        return "/booking/bookings-index";
    }

    @PostMapping("/hotel/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String findAvailableRooms(@Valid @ModelAttribute("hotelBookingForm") BookingBindingModel bookingBindingModel,
                                  BindingResult bindingResult,
                                  @PathVariable("id") Long hotelId, Principal principal,
                                  Model model) throws NotFoundException {

        HotelDetailsViewModel hotel = this.modelMapper.
                map(this.hotelService.findById(hotelId), HotelDetailsViewModel.class);

        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelBookingForm", bookingBindingModel);
            model.addAttribute("hotel", hotel);
            return "/booking/bookings-index";
        }

        List<Room> availableRooms = bookingService.findAvailableRooms(bookingBindingModel.getGuests(), hotelId, bookingBindingModel.getCheckIn());
        model.addAttribute("bookingId", bookingService.createBooking(principal, bookingBindingModel));
        model.addAttribute("rooms", availableRooms);
        return "/booking/availableRooms";
    }

    @PostMapping("/bookHotel/{roomId}")
    public String bookHotel(@ModelAttribute(name="bookingId") Long bookingId, @PathVariable("roomId") Long roomId) {
        bookingService.updateBookingWithRoom(bookingId, roomId);
        return "redirect:/home";
    }
}