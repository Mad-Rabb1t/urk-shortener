package app.repo;

import app.entity.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPassRepo extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByToken(String token);

    void deleteByToken(String token);

}
