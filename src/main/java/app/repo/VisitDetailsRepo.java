package app.repo;

import app.entity.VisitDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitDetailsRepo extends JpaRepository<VisitDetails, Long> {
    <S extends VisitDetails> S save(S s);
}
