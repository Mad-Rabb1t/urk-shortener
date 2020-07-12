package app.controller;


import app.service.ResetPasswordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forgot")
public class ForgotPasswordController {

    private final ResetPasswordService reset;

    public ForgotPasswordController(ResetPasswordService reset) {
        this.reset = reset;
    }

    @GetMapping
    public String getHandler() {
        return "forgot-password";
    }

    @PostMapping()
    public String postHandler(@RequestParam String email, Model model) {
        model.addAttribute("rightEmail", reset.isPresentEmail(email));
        return reset.canSendMessage(email) ? "email-sent" : "forgot-password";
    }
}
