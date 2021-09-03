package basridrm.travelapp.web.controller;

import basridrm.travelapp.dto.binding.DestinationBindingModel;
import basridrm.travelapp.service.implementations.DestinationServiceImpl;
import org.dom4j.rule.Mode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String addDestination(DestinationBindingModel destinationBindingModel, Model model) {
        model.addAttribute("destinations", this.destinationService.findAll());
        return "destination/destinations-add";
    }
}