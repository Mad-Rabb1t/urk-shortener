package app.controller;


import app.entity.ShortURL;
import app.service.ConvertAndSaveService;
import app.service.GetUrlsService;
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

    @GetMapping(path = "/{user_id}")
    public String handler(@PathVariable long user_id, Model model) {
        List<ShortURL> all = getService.getAllUrlsByUserId(user_id);
        //The following address is subject to change.
        model.addAttribute("mapping", "http:localhost:9004/short");
        model.addAttribute("urls", all);
        return "main-page";
    }


    @PostMapping(path = "/{user_id}")
    public RedirectView postHandling(@RequestParam String fullUrl, @PathVariable long user_id) throws NoSuchFieldException {
        return saveService.canSave(fullUrl, user_id) ? new RedirectView(String.format("%s/%d", "/main", user_id))
                : new RedirectView("/error/forbidden");
    }
}
