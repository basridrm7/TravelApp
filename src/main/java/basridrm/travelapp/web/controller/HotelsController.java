package basridrm.travelapp.web.controller;

import basridrm.travelapp.dto.binding.HotelBindingModel;
import basridrm.travelapp.dto.view.HotelDetailsViewModel;
import basridrm.travelapp.service.implementations.DestinationServiceImpl;
import basridrm.travelapp.service.implementations.HotelServiceImpl;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/hotels")
public class HotelsController {

    private final DestinationServiceImpl destinationService;
    private final HotelServiceImpl hotelService;
    private final ModelMapper modelMapper;

    public HotelsController(DestinationServiceImpl destinationService, HotelServiceImpl hotelService, ModelMapper modelMapper) {
        this.destinationService = destinationService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getHotelsPage(Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("hotels", this.hotelService.findAll());
        return "/hotel/hotels-index";
    }

    @GetMapping("/destination/{id}")
    public String getHotelsByDestination(@PathVariable("id") Long destinationId, Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        model.addAttribute("hotels", this.hotelService.findAllByDestination(destinationId));
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

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditHotelForm(@PathVariable("id") Long hotelId, Model model) throws NotFoundException {
        model.addAttribute("hotelsEditForm", this.hotelService.findById(hotelId));
        model.addAttribute("destinations", this.destinationService.findAll());
        return "hotel/hotels-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveEditedHotel(@Valid @ModelAttribute("hotelsEditForm") HotelBindingModel hotelBindingModel,
                                  BindingResult bindingResult,
                                  @PathVariable("id") Long hotelId, Model model) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hotelsEditForm", hotelBindingModel);
            model.addAttribute("destinations", this.destinationService.findAll());
            return "hotel/hotels-edit";
        }

        this.hotelService.editHotel(hotelId, hotelBindingModel);
        return "redirect:/hotels";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteHotel(@ModelAttribute(name="deleteHotelId") Long deleteHotelId) {

        this.hotelService.deleteHotel(deleteHotelId);
        return "redirect:/hotels";
    }

    @GetMapping("/hotel/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String getDetailsModal(@PathVariable("id") Long hotelId, Model model) throws NotFoundException {
        HotelDetailsViewModel hotel = this.modelMapper.
                                map(this.hotelService.findById(hotelId), HotelDetailsViewModel.class);
        model.addAttribute("hotel", hotel);

        return "/hotel/hotels-details";
    }
}