package app.service;

import app.entity.ZUser;
import app.repo.ZUserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class ZUserCreationService {
    private final ZUserRepo repo;
    private final PasswordEncoder enc;

    public ZUserCreationService(ZUserRepo repo, PasswordEncoder enc) {
        this.repo = repo;
        this.enc = enc;
    }

    //Test user creation. Should be removed
    public void create() {
        ZUser user1 = new ZUser();
        ZUser user2 = new ZUser();
        user1.email = "random@mail.ru";
        user1.username = "random@mail.ru";
        user1.password = enc.encode("123");
        user2.email = "veryrandom@mail.ru";
        user2.password = enc.encode("234");

        repo.saveAll(Arrays.asList(
                user1, user2
        ));

    }
}
