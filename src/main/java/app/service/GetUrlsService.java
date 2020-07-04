package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import app.repo.ZUserRepo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetUrlsService {

    private final RepoURL urlRepo;
    private final ZUserRepo userRepo;

    public GetUrlsService(RepoURL repo, ZUserRepo userRepo) {
        this.urlRepo = repo;
        this.userRepo = userRepo;
    }

    // gets all the urls from db registered by this user and sorts them from most frequently used to the least
    public List<ShortURL> getAllUrlsByUserId(long id) {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new)
                .getUrls().stream().sorted(Comparator.comparingLong(ShortURL::getNumOfVisits).reversed())
                .collect(Collectors.toList());
    }

    // is responsible for increasing visit count each time when there is a request to /short/{id}
    private void increaseTheVisitCount(String shortUrl) {
        ShortURL obj = urlRepo.findShortURLByShortURL(shortUrl).get();
        obj.setNumOfVisits(obj.getNumOfVisits() + 1);
        urlRepo.saveAndFlush(obj);
    }

    public boolean doesExistWithThisShortUrl(String shortUrl) {
        return urlRepo.findShortURLByShortURL(shortUrl).isPresent();
    }

    public String getFullUrl(String shortUrl) {
        increaseTheVisitCount(shortUrl);
        return urlRepo.findShortURLByShortURL(shortUrl).get().fullURL;
    }
}
