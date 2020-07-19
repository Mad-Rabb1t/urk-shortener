package app.service;

import app.entity.AccountVerifier;
import app.entity.ApplicationDetails;
import app.entity.ZUser;
import app.repo.AccountVerifierRepo;
import app.repo.ZUserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AccountVerificationService {
    private final JavaMailSender sender;
    private final AccountVerifierRepo verifierRepo;
    private final ApplicationDetails applicationDetails;
    private final ZUserRepo userRepo;

    public AccountVerificationService(JavaMailSender sender, AccountVerifierRepo verifierRepo, ApplicationDetails applicationDetails, ZUserRepo userRepo) {
        this.sender = sender;
        this.verifierRepo = verifierRepo;
        this.applicationDetails = applicationDetails;
        this.userRepo = userRepo;
    }

    private String message(String fileName) throws FileNotFoundException {
        String path = getClass().getClassLoader().getResource(fileName).getFile();
        File file = new File(path);
        if (file.exists()) return new BufferedReader(new FileReader(file)).lines().collect(Collectors.joining("\n"));
        else throw new FileNotFoundException("File with the following name could not be found" + fileName);
    }


    public void sendVerificationEmail(String email, String emailTopic, String messageResource, String token, String address)
            throws FileNotFoundException {
        String notification = String.format(message(messageResource)
                , confirmationLinkGenerator(token, address));
        SimpleMailMessage mail = new SimpleMailMessage() {{
            setTo(email);
            setSubject(emailTopic);
            setText(notification);
        }};
        sender.send(mail);
    }

    public boolean verifyAccount(String token) {
        AccountVerifier accountVerifier = verifierRepo.findByToken(token).orElseThrow(RuntimeException::new);
        ZUser user = accountVerifier.getUser();
        if (isTokenValid(token)) {
            user.setHasBeenActivated(true);
            userRepo.save(user);
            return true;
        } else {
            userRepo.delete(user);
            verifierRepo.delete(accountVerifier);
            return false;
        }
    }

    public String confirmationLinkGenerator(String token, String address) {
        return String.format("%s/%s?token=%s", applicationDetails.getRoot(), address, token);
    }

    private String generateToken() {
        boolean isNotUnique = true;
        String token = "";
        while (isNotUnique) {
            token = UUID.randomUUID().toString();
            isNotUnique = verifierRepo.findByToken(token).isPresent();
        }
        return token;
    }

    public AccountVerifier createAccountVerifier() {
        LocalDateTime dateOfExpiration = LocalDateTime.now().plusDays(1);
        AccountVerifier verifier = AccountVerifier.builder()
                .token(generateToken())
                .expirationDate(dateOfExpiration)
                .build();
        return verifierRepo.save(verifier);

    }

    private boolean isTokenValid(String token) {
        LocalDateTime expirationDate = verifierRepo.findByToken(token).orElseThrow(RuntimeException::new)
                .getExpirationDate();
        return expirationDate.isAfter(LocalDateTime.now());
    }

    public boolean hasAlreadyBeenConfirmed(String token) {
        try {
            return verifierRepo.findByToken(token).orElseThrow(NoSuchFieldException::new)
                    .getUser().hasBeenActivated;
        } catch (Exception e) {
            log.error("User with the following token does not exist: " + token, NoSuchFieldException::new);
            return false;
        }
    }
}


