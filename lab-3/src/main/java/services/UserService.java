package services;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    @Autowired
    public UserService(UserRepository userRepository){
        repo = userRepository;
    }

    public User save(User entity) {
        return repo.save(entity);
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public void deleteByEntity(User entity) {
        repo.delete(entity);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    public User update(User entity) {
        return repo.save(entity);
    }

    public Optional<User> getById(long id) {
        return repo.findById(id);
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public List<User> getAllByOwnerId(Long id){
        return repo.getAllByOwnerId(id);
    }
}
