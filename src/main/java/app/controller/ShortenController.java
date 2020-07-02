package app.controller;


import app.service.GetUrlsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/short")
public class ShortenController {

    private final GetUrlsService service;

    public ShortenController(GetUrlsService service) {
        this.service = service;
    }


//    @GetMapping("/remove")
//    public String handler() {
//        repo.deleteAll();
//        return "DELETED";
//    }

    @GetMapping(path = "{shortUrl}")
    public RedirectView handler(@PathVariable String shortUrl) {
        return new RedirectView(service.getFullUrl(shortUrl).orElseThrow(RuntimeException::new));
    }
}
