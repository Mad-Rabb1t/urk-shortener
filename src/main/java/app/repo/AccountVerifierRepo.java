package app.repo;

import app.entity.AccountVerifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountVerifierRepo extends JpaRepository<AccountVerifier, Long> {

   Optional<AccountVerifier> findByToken(String token);


}
