package app.controller;


import app.entity.ShortURL;
import app.entity.ZUserDetails;
import app.service.ConvertAndSaveService;
import app.service.GetUrlsService;
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
    private final GetUrlsService getService;

    public MainPageController(ConvertAndSaveService service, GetUrlsService urlService) {
        this.saveService = service;
        this.getService = urlService;
    }

    @GetMapping()
    public String handler(Model model, Authentication auth) {
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        long user_id = curr_user.getUser().getUser_id();
        List<ShortURL> all = getService.getAllUrlsByUserId(user_id);
        //The following address is subject to change.
        model.addAttribute("mapping", "http:localhost:9004/short");
        model.addAttribute("urls", all);
        return "main-page";
    }


    @PostMapping()
    public RedirectView postHandling(@RequestParam String fullUrl, Authentication auth)
            throws NoSuchFieldException {
        ZUserDetails curr_user = (ZUserDetails) auth.getPrincipal();
        long user_id = curr_user.getUser().getUser_id();
        return saveService.canSave(fullUrl, user_id) ? new RedirectView("/main")
                : new RedirectView("/error/forbidden");
    }
}
