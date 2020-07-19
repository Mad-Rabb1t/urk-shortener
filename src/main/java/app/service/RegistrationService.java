package app.service;

import app.entity.AccountVerifier;
import app.entity.ZUser;
import app.repo.ZUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Log4j2
@Service
public class RegistrationService {
    private final ZUserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AccountVerificationService verificationService;

    public RegistrationService(ZUserRepo userRepo, PasswordEncoder encoder, AccountVerificationService verificationService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.verificationService = verificationService;
    }

    public boolean checkPasswordConfirmation(ZUser user) {
        return user.password.equals(user.passwordConfirmation);
    }

    public boolean checkEmailUniqueness(ZUser user) {
        return !userRepo.findZUserByEmail(user.email).isPresent();
    }

    public boolean canSave(ZUser user) throws FileNotFoundException {
        AccountVerifier new_verifier = verificationService.createAccountVerifier();
        if (checkPasswordConfirmation(user) && checkEmailUniqueness(user)) {
            ZUser build = ZUser.builder()
                    .username(user.username)
                    .password(encoder.encode(user.password))
                    .email(user.email)
                    .verifier(new_verifier)
                    .hasBeenActivated(false)
                    .build();
            userRepo.save(build);
            verificationService.sendVerificationEmail(user.email, "Account Confirmation",
                    "messages/Confirmation.txt", new_verifier.token, "confirm");
            return true;
        }
        return false;
    }


}
