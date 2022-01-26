package basridrm.travelapp.web.controller;

import basridrm.travelapp.dto.binding.DestinationBindingModel;
import basridrm.travelapp.service.implementations.DestinationServiceImpl;
import javassist.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/destinations")
public class DestinationsController {

    private final DestinationServiceImpl destinationService;

    public DestinationsController(DestinationServiceImpl destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public String getDestinationsPage(Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        return "/destination/destinations-index";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addDestination(@ModelAttribute DestinationBindingModel destinationBindingModel, Model model) {

        model.addAttribute("destinationsAddForm", destinationBindingModel);
        return "destination/destinations-add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveAddedDestination(@Valid @ModelAttribute("destinationsAddForm") DestinationBindingModel destinationBindingModel,
                                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("destinationsAddForm", destinationBindingModel);
            return "destination/destinations-add";
        }

        this.destinationService.addDestination(destinationBindingModel);
        //this.destinationService.addDestination(destinationBindingModel);
        return "redirect:/destinations";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getEditDestinationForm(@PathVariable("id") Long destinationId, Model model) throws NotFoundException {
        model.addAttribute("destinationsEditForm", this.destinationService.findById(destinationId));
        return "destination/destinations-edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveEditedDestination(@Valid @ModelAttribute("destinationsEditForm") DestinationBindingModel destinationBindingModel,
                                        BindingResult bindingResult,
                                        @PathVariable("id") Long destinationId, Model model) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("destinationsEditForm", destinationBindingModel);
            return "destination/destinations-edit";
        }

        this.destinationService.editDestination(destinationId, destinationBindingModel);
        return "redirect:/destinations";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDestination(@ModelAttribute(name="deleteId") Long deleteId) {

        this.destinationService.deleteDestination(deleteId);
        return "redirect:/destinations";
    }

}