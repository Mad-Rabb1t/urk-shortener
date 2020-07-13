package app.service;

import app.entity.PasswordReset;
import app.entity.ZUser;
import app.repo.ResetPassRepo;
import app.repo.ZUserRepo;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Service
public class ResetPasswordService {
    private final JavaMailSender sender;
    private final ZUserRepo userRepo;
    private final ResetPassRepo passRepo;
    private final PasswordEncoder encoder;


    public ResetPasswordService(JavaMailSender sender, ZUserRepo repo, ResetPassRepo passRepo, PasswordEncoder encoder) {
        this.sender = sender;
        this.userRepo = repo;
        this.passRepo = passRepo;
        this.encoder = encoder;
    }

    public boolean isPresentEmail(String email) {
        return userRepo.findZUserByEmail(email).isPresent();
    }

    public boolean canResetToken(String token) {
        return passRepo.findByToken(token).isPresent() && isTokenExpired(token);
    }

    public String createLink(String email) {
        String token = UUID.randomUUID().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        PasswordReset builder = PasswordReset.builder().token(token)
                .expirationDate(formatter.format(LocalDateTime.now()))
                .user(userRepo.findZUserByEmail(email).orElseThrow(NullPointerException::new))
                .build();
        passRepo.save(builder);
        return String.format("http://localhost:9004/reset/%s", token);
    }

    public boolean isTokenExpired(String token) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String expirationDate = passRepo.findByToken(token).orElseThrow(NullPointerException::new).expirationDate;
        LocalDateTime localDateTime = LocalDateTime.parse(expirationDate, formatter);
        return LocalDateTime.now().minusHours(1).isBefore(localDateTime);
    }

    @SneakyThrows
    public ZUser findUserByResToken(String token) {
        return passRepo.findByToken(token).orElseThrow(NullPointerException::new).getUser();
    }

    public boolean checkPasswordConfirm(String password, String confirmPass) {
        return password.equals(confirmPass);
    }

    @Transactional
    public boolean resetPassword(String token, String password, String confirm) {
        if (checkPasswordConfirm(password, confirm)) {
            ZUser user = findUserByResToken(token);
            user.setPassword(encoder.encode(password));
            userRepo.save(user);
            passRepo.deleteByToken(token);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteIfExpired() {
        passRepo.findAll().stream().filter(t -> !isTokenExpired(t.token))
                .forEach(passwordReset -> passRepo.deleteByToken(passwordReset.token));
    }

    public boolean canSendMessage(String email) {
        if (isPresentEmail(email)) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Reset password");
            message.setText(String.format(
                    "Hi,\nWe got a request to reset your password. To start the process please click the following link:\n" +
                            "%s\nIf you did not make this request, simply ignore this message.", createLink(email)
            ));
            this.sender.send(message);
            return true;
        }
        return false;
    }
}