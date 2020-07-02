package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetFullUrlService {

    private final RepoURL repo;


    public GetFullUrlService(RepoURL repo) {
        this.repo = repo;
    }

    public List<ShortURL> getAll() {
        return repo.findAll();
    }

    public Optional<String> getFullUrl(String shortUrl) {
        boolean present = repo.findShortURLByShortURL(shortUrl).isPresent();
        if (present) {
            return Optional.of(repo.findShortURLByShortURL(shortUrl).get().fullURL);
        } else return Optional.empty();
    }
}
