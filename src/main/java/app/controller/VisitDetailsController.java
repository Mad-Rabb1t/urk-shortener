package app.controller;

import app.entity.VisitDetails;
import app.entity.ZUserDetails;
import app.service.UrlsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/details")
public class VisitDetailsController {

    private final UrlsService urlsService;

    @PostMapping
    public String handler(Model model, Authentication auth, @RequestParam(defaultValue = "") String shortUrl){
        if (shortUrl.equals("")) return "error-page-unexpected";
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        model.addAttribute("username", curr_user.getUser().getUsername());
        List<VisitDetails> visitDetails = urlsService.getVisitDetailsByShortURL(shortUrl);
        model.addAttribute("curr_short_url", shortUrl);
        model.addAttribute("details", visitDetails);
        return "visit-details";
    }
}
