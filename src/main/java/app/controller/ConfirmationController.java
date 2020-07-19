package app.controller;

import app.service.AccountVerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/confirm")
public class ConfirmationController {
    private final AccountVerificationService verificationService;

    public ConfirmationController(AccountVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping
    public String handle(@RequestParam String token) {
        // Firstly, we check whether account has already been confirmed.
        // If not, we check expiration date of token and
        // forward the user to confirmed-account page.
        return verificationService.hasAlreadyBeenConfirmed(token)
                ? "already-confirmed" :
                verificationService.verifyAccount(token)
                        ? "confirmed-account" : "error-page-401";
    }
}
