package app.controller;

import app.entity.ZUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/start")
public class GetStartedController {

    @GetMapping
    public String handler(Model model, Authentication auth) {
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        model.addAttribute("username", curr_user.getUser().getUsername());
        return "landing";
    }
}
