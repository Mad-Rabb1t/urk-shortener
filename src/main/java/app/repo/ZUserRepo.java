package app.repo;


import app.entity.ZUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ZUserRepo extends JpaRepository<ZUser, Long> {
    Optional<ZUser> findZUserByEmail(String email);
    Optional<ZUser> findByUsername(String username);
}
