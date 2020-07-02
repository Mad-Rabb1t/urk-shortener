package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class ConvertAndSaveService {

    private final RepoURL repo;
    private final RequestService reqService;

    public ConvertAndSaveService(RepoURL repo, RequestService reqService) {
        this.repo = repo;
        this.reqService = reqService;
    }

    private boolean hasBeenProcessedBefore(String fullUrl) {
        return repo.findShortURLByFullURL(fullUrl).isPresent();
    }

    private boolean isShortUrlUnique(String shortUrl) {
        return !repo.findShortURLByShortURL(shortUrl).isPresent();
    }


    public boolean canSave(String fullURL) {
        String randomString;
        if (hasBeenProcessedBefore(fullURL)) return false;
        while (true) {
            randomString = reqService.autoRandomGenerator();
            if (isShortUrlUnique(randomString)) break;
        }

        ShortURL shorterUrl = ShortURL.builder()
                .shortURL(randomString)
                .fullURL(fullURL)
                .numOfVisits(0)
                .dateOfCreation(findCurrTime())
                .build();
        repo.save(shorterUrl);
        return true;
    }

    private String findCurrTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(LocalDate.now());
    }
}



