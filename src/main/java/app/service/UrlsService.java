package app.service;


import app.entity.ShortURL;
import app.entity.VisitDetails;
import app.repo.RepoURL;
import app.repo.VisitDetailsRepo;
import app.repo.ZUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UrlsService {

    private final RepoURL urlRepo;
    private final ZUserRepo userRepo;
    private final VisitDetailsRepo detailsRepo;


    // gets all the urls from db registered by this user and sorts them from most frequently used to the least
    public List<ShortURL> getAllUrlsByUserId(long id) {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new)
                .getUrls().stream().sorted(Comparator.comparingLong(ShortURL::getNumOfVisits).reversed())
                .collect(Collectors.toList());
    }
    // gets limited amount of urls for pagination
    public List<ShortURL> getLimitedUrlsByUserId(long id, int amount) {
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new)
                .getUrls().stream().limit(amount).sorted(Comparator.comparingLong(ShortURL::getNumOfVisits).reversed())
                .collect(Collectors.toList());
    }

    public int getUrlsCount(long id){
        return userRepo.findById(id).orElseThrow(NoSuchElementException::new).getUrls().size();
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

    public List<VisitDetails> getVisitDetailsByShortURL(String shortUrl){
        return new ArrayList<>(urlRepo.findShortURLByShortURL(shortUrl).get().getVisitDetails());
    }

    public void createVisitDetails(String url_id, String ipAddress, String browser_info, String os, String lat_lon, String city_country, String org) {
        ShortURL shortURL_object = urlRepo.findShortURLByShortURL(url_id)
                .orElseThrow(RuntimeException::new);
        String curr_date = DateTimeFormatter.ofPattern("dd/MM/YYYY").format(LocalDate.now());

        detailsRepo.save(VisitDetails.builder()
                .visit_date(curr_date)
                .ip_address(ipAddress)
                .browser_info(browser_info)
                .os(os)
                .lat_lon(lat_lon)
                .city_country(city_country)
                .org(org)
                .short_url(shortURL_object)
                .build());
    }
}
