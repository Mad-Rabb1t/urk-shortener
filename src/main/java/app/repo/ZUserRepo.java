package app.repo;


import app.entity.ZUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ZUserRepo extends JpaRepository<ZUser, Long> {

}
