package app.entity;

import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@Value
public class ApplicationDetails {
    String root = String.format("http://localhost:%s", System.getenv("PORT"));
//            "https://iba-short-url.herokuapp.com";
}
