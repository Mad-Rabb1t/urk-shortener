package app.controller;

import app.entity.ZUser;
import app.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;

@Log4j2
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
    public String postHandler(ZUser user, Model model) throws FileNotFoundException {
        model.addAttribute("correctPassword", regService.checkPasswordConfirmation(user));
        model.addAttribute("correctEmail", regService.checkEmailUniqueness(user));
        model.addAttribute("Reset", false);
        return regService.canSave(user) ? "email-sent" : "registration";
    }
}
