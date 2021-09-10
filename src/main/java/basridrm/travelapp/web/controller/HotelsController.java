package basridrm.travelapp.web.controller;

import basridrm.travelapp.dto.binding.HotelBindingModel;
import basridrm.travelapp.service.implementations.DestinationServiceImpl;
import basridrm.travelapp.service.implementations.HotelServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/hotels")
public class HotelsController {

    private final DestinationServiceImpl destinationService;
    private final HotelServiceImpl hotelService;

    public HotelsController(DestinationServiceImpl destinationService, HotelServiceImpl hotelService) {
        this.destinationService = destinationService;
        this.hotelService = hotelService;
    }

    @GetMapping
    public String getHotelsPage(Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("hotels", this.hotelService.findAll());
        return "/hotel/hotels-index";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addHotel(@ModelAttribute HotelBindingModel hotelBindingModel, Model model) {
        model.addAttribute("hotelsAddForm", hotelBindingModel);
        model.addAttribute("destinations", this.destinationService.findAll());
        return "/hotel/hotels-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveAddedHotel(@Valid @ModelAttribute("hotelsAddForm") HotelBindingModel hotelBindingModel,
                                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelsAddForm", hotelBindingModel);
            model.addAttribute("destinations", this.destinationService.findAll());
            return "/hotel/hotels-add";
        }

        this.hotelService.addHotel(hotelBindingModel);
        return "redirect:/hotels";
    }
}