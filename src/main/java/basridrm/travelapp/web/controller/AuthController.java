package basridrm.travelapp.web.controller;

import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.data.repository.UserRepository;
import basridrm.travelapp.dto.binding.auth.UserRegisterBindingModel;
import basridrm.travelapp.service.implementations.RoleServiceImpl;
import basridrm.travelapp.service.implementations.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final UserServiceImpl userService;

    public AuthController(UserServiceImpl userService, UserRepository userRepository, RoleServiceImpl roleService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login() {
        User user = userService.getPrincipal();
        if (user != null) {
            return "home";
        }
        return "auth/login";
    }

    @GetMapping("/home")
    public String getHomeForLoggedUsers() {
        return "home";
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String getRegisterForm(@ModelAttribute UserRegisterBindingModel userRegisterBindingModel,
                                  Model model) {
        model.addAttribute("userRegisterForm", userRegisterBindingModel);
        return "auth/register";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(@Valid @ModelAttribute("userRegisterForm") UserRegisterBindingModel user,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(userService.emailExists(user.getEmail())) {
            bindingResult.addError(new FieldError("userRegisterBindingModel", "email",
                    "Email address already in use"));
        }
        if(userService.usernameExists(user.getUsername())) {
            bindingResult.addError(new FieldError("userRegisterBindingModel", "username",
                    "Username already in use"));
        }
        if(bindingResult.hasErrors()) {
            return "auth/register";
        }

        redirectAttributes.addFlashAttribute("messageAlert",
                "Registration successfully completed!");
        userService.register(user);

        return "redirect:/login";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }
}
