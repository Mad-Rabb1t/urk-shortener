package app.controller;


import app.entity.ShortURL;
import app.service.ConverterService;
import app.service.GetFullUrlService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/main")
public class MainPageController {

    private final ConverterService service;
    private final GetFullUrlService urlService;

    public MainPageController(ConverterService service, GetFullUrlService urlService) {
        this.service = service;
        this.urlService = urlService;
    }

    @GetMapping
    public String handler(Model model) {

        List<ShortURL> all = urlService.getAll();
        //The following address is subject to change.
        model.addAttribute("mapping","http:localhost:9004/short/");
        model.addAttribute("urls",all);
        return "main-page";
    }


    @PostMapping
    public String postHandling(@RequestParam String fullUrl){
        System.out.println(fullUrl);
        service.save(fullUrl);
        return "success";
    }
}
