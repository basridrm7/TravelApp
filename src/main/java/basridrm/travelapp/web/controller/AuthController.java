package basridrm.travelapp.web.controller;

import basridrm.travelapp.data.entity.User;
import basridrm.travelapp.dto.binding.auth.UserRegisterBindingModel;
import basridrm.travelapp.dto.view.UserProfileViewModel;
import basridrm.travelapp.service.implementations.UserServiceImpl;
import org.modelmapper.ModelMapper;
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
import java.security.Principal;

@Controller
public class AuthController {

    //private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public AuthController(UserServiceImpl userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String getLoginForm() {
        User user = userService.getPrincipal();
        if (user != null) {
            return "home";
        }
        return "auth/login";
    }

    @GetMapping("/home")
    public String getHomeForLoggedUsers(Model model) {
        model.addAttribute("user", userService.getPrincipal());
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

    @GetMapping("/auth/profile")
    public String getProfile(Principal principal, Model model) {
        UserProfileViewModel user = this.modelMapper
                                    .map(this.userService.loadUserByUsername(principal.getName()),
                                            UserProfileViewModel.class);
        model.addAttribute("userProfile", user);
        return "auth/profile";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor editor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, editor);
    }
}