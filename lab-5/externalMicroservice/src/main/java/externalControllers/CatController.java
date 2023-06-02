package externalControllers;

import catConsumers.CatKafkaConsumer;
import catProducers.CatKafkaProducer;
import entities.Cat;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import externalServices.UserService;

import java.util.List;

@RestController
@RequestMapping("/cats")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CatController {
    private final UserService userService;

    private final CatKafkaProducer producer;

    private final CatKafkaConsumer consumer;

    @Autowired
    public CatController(UserService userService, CatKafkaProducer producer, CatKafkaConsumer consumer) {
        this.userService = userService;
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> getCatById(@PathVariable Long id) {
        producer.sendGetCatByIdMessage(id);
        long ownerId = consumer.getResult().getBody().get(0).getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            return consumer.getResult();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/getByOwnerId/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> getCatsByOwnerId(@PathVariable Long id) {
        if (id == getCurrentUserId() || isAdmin()) {
            producer.sendGetCatsByOwnerIdMessage(id);
            return consumer.getResult();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/getAllByName/{name}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> getAllCatsByName(@PathVariable String name) {
        if (isAdmin()) {
            producer.sendGetCatsByNameMessage(name);
            return consumer.getResult();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Cat>> getAllCats() {
        producer.sendGetAllCatsMessage();
        return consumer.getResult();
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> createCat(@RequestBody Cat cat) {
        producer.sendCreateCatMessage(cat);
        return consumer.getResult();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> updateCat(@RequestBody Cat cat) {
        producer.sendUpdateCatMessage(cat);
        long ownerId = consumer.getResult().getBody().get(0).getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            return consumer.getResult();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        producer.sendDeleteCatByIdMessage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByEntity")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteCatByEntity(@RequestBody Cat cat) {
        producer.sendDeleteCatByEntityMessage(cat);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllCats() {
        producer.sendDeleteAllCatsMessage();
        return ResponseEntity.ok().build();
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByLogin(username);
        return user.getCatOwnerId();
    }
}