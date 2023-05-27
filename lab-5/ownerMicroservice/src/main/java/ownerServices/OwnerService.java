package ownerServices;

import entities.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ownerRepositories.OwnerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {
    private final OwnerRepository repo;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository){
        repo = ownerRepository;
    }

    public List<Owner> getAllByName(String name){
        return repo.getAllByName(name);
    }

    public Owner save(Owner entity) {
        return repo.save(entity);
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public void deleteByEntity(Owner entity) {
        repo.delete(entity);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public Owner update(Owner entity) {
        return repo.save(entity);
    }

    public Optional<Owner> getById(long id) {
        return repo.findById(id);
    }

    public List<Owner> getAll() {
        return repo.findAll();
    }
}