package app.service;


import app.entity.ShortURL;
import app.repo.RepoURL;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class ConverterService {

    private final RepoURL repo;

    private final static String urlWithParams =
            "https://www.random.org/strings/?num=1&len=6&digits=on&upperalpha=off&loweralpha=on&unique=on&format=plain&rnd=new";

    public ConverterService(RepoURL repo) {
        this.repo = repo;
    }

    private boolean hasBeenProcessedBefore(String fullUrl) {
        return repo.findShortURLByFullURL(fullUrl).isPresent();
    }

    private boolean canBeShortenedTo(String shortUrl) {
        return !repo.findShortURLByShortURL(shortUrl).isPresent();
    }

    private String findCurrTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return formatter.format(now);
    }

    public boolean save(String fullURL) {
        if (hasBeenProcessedBefore(fullURL)) return false;
        String randomString = generateRandomString();
        ShortURL shortened = ShortURL.builder()
                .shortURL(randomString)
                .fullURL(fullURL)
                .numOfVisits(0)
                .dateOfCreation(findCurrTime())
                .build();
        ShortURL addedTodDB = repo.save(shortened);
        return true;
    }

    private String generateRandomString() {
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders() {{
            add("API Key", "333c9798-285a-4cc7-a659-db1c01f0a6ea");
            add("Hashed API Key", "EbzDs4wpnyxkIVqYpxWHI6Wzqso0Jet2b/hh5nNOGtHwpRV1Y71lCrHEdHD/j0/BtqagISeA6SJ5p5hXi9RqRg==");
            add("Key Name", "MyProject");
        }};
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> result = rest.exchange(urlWithParams, HttpMethod.POST, httpEntity, String.class);

        return result.getBody().trim();
    }
}



