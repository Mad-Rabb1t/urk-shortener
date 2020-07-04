package app.controller;


import app.repo.RepoURL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remove")
public class RemoveDatabaseController {

    private final RepoURL repo;

    public RemoveDatabaseController(RepoURL repo) {
        this.repo = repo;
    }

    @GetMapping
    public String handler() {
        repo.deleteAll();
        return "DB is cleaned";
    }
}
