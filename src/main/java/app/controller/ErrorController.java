package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        return "error-page-403";
    }

    @GetMapping("/bad-request")
    public String handleBadRequest() {
        return "error-page-400";
    }

    @GetMapping("/expired")
    public String handleExpiredToken() {
        return "error-page-401";
    }

    @GetMapping("/internalError")
    public String handleAPIError() {
        return "error-page-500";
    }

    @ExceptionHandler(Exception.class)
    public String handleUnexpectedException()
    {
        return "error-page-unexpected";
    }

}
