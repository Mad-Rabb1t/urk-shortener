package app.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

// Done!
@Log4j2
@Service
public class RequestService {
    //This service makes a request to random.org to get random strings each time for creating unique mapping(for new full url)
    // Dealing with API exceptions
    private ResponseEntity<String> makeRequestToExternalServer(String url, HttpHeaders headers) {
        try {
            RestTemplate rest = new RestTemplate();
            HttpEntity<String> httpEntity = new HttpEntity<>(headers);
            return rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        } catch (Exception e) {
            log.error("The following error occurred while using external API (random.org)---> " + e);
            log.info("Error was thrown inside " + getClass());
            throw new RuntimeException("External API ERROR --->" + e);
        }
    }


    private HttpHeaders getHeadersForRandomOrg() {
        return new HttpHeaders() {{
            add("API Key", "333c9798-285a-4cc7-a659-db1c01f0a6ea");
            add("Hashed API Key", "EbzDs4wpnyxkIVqYpxWHI6Wzqso0Jet2b/hh5nNOGtHwpRV1Y71lCrHEdHD/j0/BtqagISeA6SJ5p5hXi9RqRg==");
            add("Key Name", "MyProject");
        }};
    }


    public String autoRandomGenerator() {
        final String urlWithParams =
                //working url:
                "https://www.random.org/strings/?num=1&len=6&digits=on&upperalpha=off&loweralpha=on&unique=on&format=plain&rnd=new";
        //urls with an error:
//                "https://www.random.org/strings/wrong-url";
//        "https://www.random.org/strings/?num=100&len=1&digits=on&upperalpha=off&loweralpha=on&unique=on&format=plain&rnd=new";
        ResponseEntity<String> response = makeRequestToExternalServer(urlWithParams, getHeadersForRandomOrg());
        return Objects.requireNonNull(response.getBody()).trim();
    }
}
