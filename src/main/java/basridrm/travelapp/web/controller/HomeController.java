package basridrm.travelapp.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public String getHome() {
        return "index";
    }
}