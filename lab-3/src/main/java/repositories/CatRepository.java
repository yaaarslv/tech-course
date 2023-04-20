package repositories;

import entities.Cat;
import entities.Flea;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier("catRepository")
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> getAllByOwnerId(Long id);
    List<Cat> getAllByName(String name);
}