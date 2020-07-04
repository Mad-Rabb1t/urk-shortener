package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import app.repo.ZUserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Implemented
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


    // Temporary implementation
    public boolean canSave(String fullURL, long user_id) throws NoSuchFieldException {
        String randomString;
        if(hasBeenProcessedBeforeByUserId(fullURL,user_id)) return false;
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

    private String findCurrTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(LocalDate.now());
    }
}



