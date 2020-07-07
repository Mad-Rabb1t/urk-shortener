package app.service;

import app.entity.ZUser;
import app.repo.ZUserRepo;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
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
        return userRepo.findAll().stream()
                .noneMatch(us -> us.email.equals(user.email)
                );
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
