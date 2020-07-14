package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import app.repo.ZUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Implemented
@Log4j2
@Service
public class ConvertAndSaveService {
    private final RepoURL urlRepo;
    private final ZUserRepo userRepo;
    private final RequestService reqService;

    public ConvertAndSaveService(RepoURL repo, ZUserRepo userRepo, RequestService reqService) {
        this.urlRepo = repo;
        this.userRepo = userRepo;
        this.reqService = reqService;
    }

    // this method ensures that entered url is unique(has not been created before by this user)
    private boolean hasBeenProcessedBeforeByUserId(String fullUrl, long user_id) throws NoSuchFieldException {
        return userRepo.findById(user_id).orElseThrow(NoSuchFieldException::new).getUrls().stream()
                .anyMatch(existingUrl -> existingUrl.fullURL.equals(fullUrl));
    }

    private boolean isShortUrlUnique(String shortUrl) {
        return !urlRepo.findShortURLByShortURL(shortUrl).isPresent();
    }


    public boolean canSave(String fullURL, long user_id) throws NoSuchFieldException {
        String randomString;
        if (hasBeenProcessedBeforeByUserId(fullURL, user_id)) return false;
        while (true) {
            randomString = reqService.autoRandomGenerator();
            if (isShortUrlUnique(randomString)) break;
        }

        ShortURL shorterUrl = ShortURL.builder()
                .shortURL(randomString)
                .fullURL(fullURL)
                .numOfVisits(0)
                .dateOfCreation(findCurrTime())
                .user(userRepo.findById(user_id).orElseThrow(NoSuchFieldError::new))
                .build();
        urlRepo.save(shorterUrl);
        return true;
    }

    // implemented on 14.07.2020
    // checks whether the full url which is entered is really a link.
    public boolean isValidUrl(String fullUrl) {
        try {
            new URL(fullUrl).toURI();
        } catch (Exception e) {
            String message = String.format("The URL entered is not valid: %s ---Therefore,the following exception occurred: %s"
                    , fullUrl, e);
            log.error(message);
            return false;
        }
        return true;
    }

    private String findCurrTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(LocalDate.now());
    }
}



