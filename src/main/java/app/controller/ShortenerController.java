package app.controller;


import app.entity.ShortURL;
import app.service.GetFullUrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/short")
public class ShortenerController {

    private final GetFullUrlService service;

    public ShortenerController(GetFullUrlService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Integer> handler(){
//        return service.getAll().stream().map(u->u.shortURL.length()).collect(Collectors.toList());
//    }

    @GetMapping(path = "{shortUrl}")
    public RedirectView handler(@PathVariable String shortUrl){
        return new RedirectView(service.getFullUrl(shortUrl).get());
    }




}
