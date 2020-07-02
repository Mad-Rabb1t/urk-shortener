package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetUrlsService {

    private final RepoURL repo;

    public GetUrlsService(RepoURL repo) {
        this.repo = repo;
    }

    public List<ShortURL> getAll() {
        return repo.findAll();
    }

    // is responsible for increasing visit count each time when there is a request to /short/{id}
    private void increaseTheVisitCount(String shortUrl) {
        ShortURL obj = repo.findShortURLByShortURL(shortUrl).get();
        obj.setNumOfVisits(obj.getNumOfVisits() + 1);
        repo.saveAndFlush(obj);
    }

    public Optional<String> getFullUrl(String shortUrl) {
        boolean present = repo.findShortURLByShortURL(shortUrl).isPresent();
        if (present) {
            increaseTheVisitCount(shortUrl);
            return Optional.of(repo.findShortURLByShortURL(shortUrl).get().fullURL);
        } else return Optional.empty();
    }
}
