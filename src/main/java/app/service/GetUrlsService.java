package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUrlsService {

    private final RepoURL repo;

    public GetUrlsService(RepoURL repo) {
        this.repo = repo;
    }

    // gets all the urls from db and sorts them from most frequently used to the least.
    public List<ShortURL> getAll() {
        return repo.findAll().stream().sorted(Comparator.comparingLong(ShortURL::getNumOfVisits).reversed())
                .collect(Collectors.toList());
    }

    // is responsible for increasing visit count each time when there is a request to /short/{id}
    private void increaseTheVisitCount(String shortUrl) {
        ShortURL obj = repo.findShortURLByShortURL(shortUrl).get();
        obj.setNumOfVisits(obj.getNumOfVisits() + 1);
        repo.saveAndFlush(obj);
    }

    public boolean doesExistWithThisShortUrl(String shortUrl) {
        return repo.findShortURLByShortURL(shortUrl).isPresent();
    }

    public String getFullUrl(String shortUrl) {
        increaseTheVisitCount(shortUrl);
        return repo.findShortURLByShortURL(shortUrl).get().fullURL;
    }
}
