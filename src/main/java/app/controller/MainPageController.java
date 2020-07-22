package app.controller;


import app.entity.ApplicationDetails;
import app.entity.ShortURL;
import app.entity.ZUserDetails;
import app.service.ConvertAndSaveService;
import app.service.UrlsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
@Controller
@RequestMapping("/main")
public class MainPageController {

    private final ConvertAndSaveService saveService;
    private final UrlsService getService;
    private final ApplicationDetails applicationDetails;

    public MainPageController(ConvertAndSaveService service, UrlsService urlService, ApplicationDetails applicationDetails) {
        this.saveService = service;
        this.getService = urlService;
        this.applicationDetails = applicationDetails;
    }

    @GetMapping()
    public String handler(Model model, Authentication auth, @RequestParam(defaultValue = "") String page_index) {
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        long user_id = curr_user.getUser().getUser_id();
//        List<ShortURL> all = getService.getAllUrlsByUserId(user_id);
        int index;
        try {
            index = Integer.parseInt(page_index);
            if (index < 1) index = 1;
        } catch (NumberFormatException | NullPointerException ex) {
            index = 1;
        }
        List<ShortURL> all = getService.getLimitedUrlsByUserId(user_id, index * 10); //get 10 urls from db per time
        model.addAttribute("page_index", index);
        model.addAttribute("record_count", getService.getUrlsCount(user_id));
        String shortUrl = String.format("%s/%s", applicationDetails.getRoot(), "sh");
        model.addAttribute("mapping", shortUrl);
        model.addAttribute("urls", all);
        String username = curr_user.getUser().getUsername();
        model.addAttribute("username", username);
        return "main-page";
    }


    @PostMapping()
    public RedirectView postHandling(@RequestParam String fullUrl, Authentication auth) {
        if (!saveService.isValidUrl(fullUrl)) return new RedirectView("/error/bad-request");
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        long user_id = curr_user.getUser().getUser_id();
        try {
            return saveService.canSave(fullUrl, user_id) ? new RedirectView("/main")
                    : new RedirectView("/error/forbidden");
        } catch (Exception e) {
            return new RedirectView("/error/internalError");
        }
    }
}
