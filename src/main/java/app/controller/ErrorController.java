package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/not-found")
    public String handleNotFoundException() {
        return "error-page-404";
    }

    @GetMapping("/forbidden")
    public String handleForbiddenAction() {
        return "error-page-406";
    }
}
