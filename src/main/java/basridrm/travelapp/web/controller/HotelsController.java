package basridrm.travelapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HotelsController {

    @GetMapping("/hotels")
    public String getHotelsPage() {
        return "hotels";
    }
}