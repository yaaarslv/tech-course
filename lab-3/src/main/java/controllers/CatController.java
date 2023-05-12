package controllers;

import entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.CatService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cats")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<Cat> getCatById(@PathVariable Long id) {
        Optional<Cat> cat = catService.getById(id);
        return ResponseEntity.ok(cat.get());
    }

    @GetMapping("/getByOwnerId/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<List<Cat>> getCatsByOwnerId(@PathVariable Long id) {
        List<Cat> cats = catService.getAllByOwnerId(id);
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/getAllByName/{name}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<List<Cat>> getAllCatsByName(@PathVariable String name) {
        List<Cat> cats = catService.getAllByName(name);
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Cat>> getAllCats() {
        List<Cat> cats = catService.getAll();
        return ResponseEntity.ok(cats);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<Cat> createCat(@RequestBody Cat cat) {
        Cat createdCat = catService.save(cat);
        return ResponseEntity.ok(createdCat);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<Cat> updateCat(@RequestBody Cat cat) {
        Cat updatedCat = catService.update(cat);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<Void> deleteCatById(@PathVariable Long id) {
        catService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByEntity")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') and #id == authentication.principal.owner.id")
    public ResponseEntity<Void> deleteCatByEntity(@RequestBody Cat cat) {
        catService.deleteByEntity(cat);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllCats() {
        catService.deleteAll();
        return ResponseEntity.ok().build();
    }
}