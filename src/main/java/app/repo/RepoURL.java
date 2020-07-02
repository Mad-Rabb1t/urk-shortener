package app.repo;

import app.entity.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepoURL extends JpaRepository<ShortURL, Long> {

    <S extends ShortURL> S save(S s);

    Optional<ShortURL> findShortURLByFullURL(String fullURL);
    Optional<ShortURL> findShortURLByShortURL(String fullURL);

    List<ShortURL> findAll();
}
