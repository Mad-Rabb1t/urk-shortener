package app.controller;


import app.service.GetUrlsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/short")
public class ShortenController {

    private final GetUrlsService service;

    public ShortenController(GetUrlsService service) {
        this.service = service;
    }


    @GetMapping(path = "{shortUrl}")
    public RedirectView handler(@PathVariable String shortUrl, Authentication auth) {
        //Firstly, we are looking for the data with the passed short url in db, if it exists we redirect the user to
        // that url if not user is redirected to an error page.
        return service.doesExistWithThisShortUrl(shortUrl) ? new RedirectView(service.getFullUrl(shortUrl))
                : new RedirectView("/error/not-found");

    }
}
