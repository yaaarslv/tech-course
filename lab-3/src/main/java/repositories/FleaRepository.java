package repositories;

import entities.Cat;
import entities.Flea;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier("fleaRepository")
@Repository
public interface FleaRepository extends JpaRepository<Flea, Long> {
    List<Flea> getAllByCatId(Long id);
    List<Flea> getAllByName(String name);
}