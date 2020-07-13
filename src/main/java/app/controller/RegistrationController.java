package app.controller;

import app.entity.ZUser;
import app.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final RegistrationService regService;

    public RegistrationController(RegistrationService registrationService) {
        this.regService = registrationService;
    }

    @GetMapping
    public String getHandler() {
        return "registration";
    }

    @PostMapping
    public String postHandler(ZUser user, Model model) {
        model.addAttribute("correctPassword", regService.checkPasswordConfirmation(user));
        model.addAttribute("correctEmail", regService.checkEmailUniqueness(user));
        return regService.canSave(user) ? "index" : "registration";
    }
}
