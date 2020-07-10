package app.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
@Log4j2
@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/")
    public String handle() {
        return "main-page";
    }

    @GetMapping("/login")
    public String loginHandler() {
        return "index";
    }

}
