package app.controller;

import app.service.ResetPasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reset")
public class ResetPasswordController {
    public final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPass) {
        this.resetPasswordService = resetPass;
    }

    @GetMapping()
    public String handle(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        resetPasswordService.deleteIfExpired();
        return resetPasswordService.canResetToken(token) ? "new-password" : "error-page-404";
    }

    @PostMapping()
    public String postHandle(@RequestParam String token,
                             @RequestParam String password,
                             @RequestParam String confirmPassword,
                             Model model) {
        model.addAttribute("correctPassword", resetPasswordService.checkPasswordConfirm(password, confirmPassword));
        return resetPasswordService.resetPassword(token, password, confirmPassword) ? "index" : "new-password";
    }
}
