package app.service;

import app.entity.ZUser;
import app.repo.ZUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class RegistrationService {
    private final ZUserRepo userRepo;
    private final PasswordEncoder encoder;

    public RegistrationService(ZUserRepo userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    public boolean checkPasswordConfirmation(ZUser user) {
        return user.password.equals(user.passwordConfirmation);
    }

    public boolean checkEmailUniqueness(ZUser user) {
        return !userRepo.findZUserByEmail(user.email).isPresent();
    }

    public boolean canSave(ZUser user) {
        if (checkPasswordConfirmation(user) && checkEmailUniqueness(user)) {
            ZUser build = ZUser.builder().username(user.username)
                    .password(encoder.encode(user.password)).email(user.email).build();
            userRepo.save(build);
            return true;
        }
        return false;
    }

}
