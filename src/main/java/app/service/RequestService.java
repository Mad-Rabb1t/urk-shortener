package app.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class RequestService {
//This service makes a request to random.org to get random strings each time for creating unique mapping(for new full url)
    private ResponseEntity<String> makeRequestToExternalServer(String url, HttpHeaders headers) {
        RestTemplate rest = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
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
                "https://www.random.org/strings/?num=1&len=6&digits=on&upperalpha=off&loweralpha=on&unique=on&format=plain&rnd=new";
        ResponseEntity<String> response = makeRequestToExternalServer(urlWithParams, getHeadersForRandomOrg());
        return Objects.requireNonNull(response.getBody()).trim();
    }
}
