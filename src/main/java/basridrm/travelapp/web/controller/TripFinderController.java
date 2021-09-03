package basridrm.travelapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TripFinderController {

    @GetMapping("/trip-finder")
    public String getTripFinderPage() {
        return "trip-finder";
    }
}