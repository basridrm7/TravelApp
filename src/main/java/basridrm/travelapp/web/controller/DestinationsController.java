package basridrm.travelapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DestinationsController {

    @GetMapping("/destinations")
    public String getDestinationsPage() {
        return "destinations";
    }
}