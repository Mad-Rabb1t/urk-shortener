package app.service;

import app.entity.ZUser;
import app.repo.AccountVerifierRepo;
import app.repo.ZUserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class ZUserCreationService {
    private final ZUserRepo repo;
    private final AccountVerifierRepo verifierRepo;
    private final PasswordEncoder enc;

    public ZUserCreationService(ZUserRepo repo, AccountVerifierRepo verifierRepo, PasswordEncoder enc) {
        this.repo = repo;
        this.verifierRepo = verifierRepo;
        this.enc = enc;
    }

    //Test user creation.
    @Bean
    public void create() {
        // resets all the information in db each time application is started
        repo.deleteAll();
        verifierRepo.deleteAll();
        ZUser user1 = new ZUser();
        ZUser user2 = new ZUser();
        user1.email = "random_user@mail.ru";
        user1.username = "random_user";
        user1.hasBeenActivated = true;
        user1.password = enc.encode("22");
        user2.email = "r3xf0x@yandex.ru";
        user2.username = "Alex";
        user2.hasBeenActivated = true;
        user2.password = enc.encode("123");
        repo.saveAll(Arrays.asList(
                user1, user2
        ));


    }
}
