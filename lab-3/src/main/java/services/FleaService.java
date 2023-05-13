package services;

import entities.Flea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.FleaRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FleaService {
    private final FleaRepository repo;

    @Autowired
    public FleaService(FleaRepository fleaRepository){
        repo = fleaRepository;
    }

    public List<Flea> getAllByCatId(Long id){
        return repo.getAllByCatId(id);
    }

    public List<Flea> getAllByName(String name){
        return repo.getAllByName(name);
    }

    public Flea save(Flea entity) {
        return repo.save(entity);
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public void deleteByEntity(Flea entity) {
        repo.delete(entity);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public Flea update(Flea entity) {
        return repo.save(entity);
    }

    public Optional<Flea> getById(long id) {
        return repo.findById(id);
    }

    public List<Flea> getAll() {
        return repo.findAll();
    }
}