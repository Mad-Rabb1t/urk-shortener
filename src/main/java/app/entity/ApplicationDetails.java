package app.entity;

import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@Value
public class ApplicationDetails {
    String root = "https://iba-short-url.herokuapp.com";
}
