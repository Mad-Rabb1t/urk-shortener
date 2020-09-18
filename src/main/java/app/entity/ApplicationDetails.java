package app.entity;

import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@Value
public class ApplicationDetails {
    String root = "https://cut-ly.herokuapp.com";
//          String.format("http://localhost:%s", System.getenv("PORT"));  
}
