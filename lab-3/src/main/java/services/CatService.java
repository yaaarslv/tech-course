package services;

import entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.CatRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {
    private final CatRepository repo;

    @Autowired
    public CatService(CatRepository catRepository){
        repo = catRepository;
    }

    public List<Cat> getAllByOwnerId(Long id){
        return repo.getAllByOwnerId(id);
    }

    public List<Cat> getAllByName(String name){
        return repo.getAllByName(name);
    }

    public Cat save(Cat entity) {
        return repo.save(entity);
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public void deleteByEntity(Cat entity) {
        repo.delete(entity);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public Cat update(Cat entity) {
        return repo.save(entity);
    }

    public Optional<Cat> getById(long id) {
        return repo.findById(id);
    }

    public List<Cat> getAll() {
        return repo.findAll();
    }
}