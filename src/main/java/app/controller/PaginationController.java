package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/pagination")
public class PaginationController {

    @GetMapping
    public RedirectView handle(Model model, @RequestParam String page_index, @RequestParam String show){
        int page_index_val;
        int show_val;
        try {
            page_index_val = Integer.parseInt(page_index);
        } catch (Exception ex) {
            page_index_val = 1;
        }
        try {
            show_val = Integer.parseInt(show);
            if (show_val != 1 && show_val != -1){
                show_val = 0;
            }
        } catch (Exception ex) {
            show_val = 0;
        }
        model.addAttribute("page_index", page_index_val + show_val);
        return new RedirectView(String.format("/main?page_index=%d", page_index_val + show_val));
    }
}
