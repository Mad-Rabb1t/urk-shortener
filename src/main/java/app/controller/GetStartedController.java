package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/start")
public class GetStartedController {

    @GetMapping
    public String handler(){
        return "landing";
    }
}
