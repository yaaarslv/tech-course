package externalControllers;

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
import catServices.CatService;
import externalServices.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cats")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CatController {
    private final CatService catService;

    private final UserService userService;

    @Autowired
    public CatController(CatService catService, UserService userService) {
        this.catService = catService;
        this.userService = userService;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Cat> getCatById(@PathVariable Long id) {
        Optional<Cat> cat = catService.getById(id);
        long ownerId = cat.get().getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            return ResponseEntity.ok(cat.get());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/getByOwnerId/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> getCatsByOwnerId(@PathVariable Long id) {
        if (id == getCurrentUserId() || isAdmin()) {
            List<Cat> cats = catService.getAllByOwnerId(id);
            return ResponseEntity.ok(cats);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/getAllByName/{name}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Cat>> getAllCatsByName(@PathVariable String name) {
        List<Cat> cats = catService.getAllByName(name);
        if (isAdmin()) {
            return ResponseEntity.ok(cats);
        } else {
            List<Cat> ownersCat = cats.stream().filter(c -> c.getOwnerId() == getCurrentUserId()).toList();
            return ResponseEntity.ok(ownersCat);
        }
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Cat>> getAllCats() {
        if (isAdmin()) {
            List<Cat> cats = catService.getAll();
            return ResponseEntity.ok(cats);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat createdCat = catService.save(cat);
        return ResponseEntity.ok(createdCat);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Cat> updateCat(@RequestBody Cat cat) {
        Cat updatedCat = catService.update(cat);
        long ownerId = updatedCat.getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            return ResponseEntity.ok(updatedCat);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        Optional<Cat> cat = catService.getById(id);
        long ownerId = cat.get().getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            catService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/deleteByEntity")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Void> deleteCatByEntity(@RequestBody Cat cat) {
        Optional<Cat> catById = catService.getById(cat.getId());
        long ownerId = catById.get().getOwnerId();
        if (ownerId == getCurrentUserId() || isAdmin()) {
            catService.deleteByEntity(cat);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllCats() {
        if (isAdmin()) {
            catService.deleteAll();
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    private long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println(username);
        User user = userService.getByLogin(username);
        return user.getCatOwnerId();
    }
}