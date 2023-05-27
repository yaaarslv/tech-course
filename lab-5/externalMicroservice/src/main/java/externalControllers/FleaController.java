package externalControllers;

import entities.Flea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fleaServices.FleaService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fleas")
public class FleaController {
    private final FleaService fleaService;

    @Autowired
    public FleaController(FleaService fleaService) {
        this.fleaService = fleaService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Flea> getFleaById(@PathVariable Long id) {
        Optional<Flea> flea = fleaService.getById(id);
        return ResponseEntity.ok(flea.get());
    }

    @GetMapping("/getByCatId/{id}")
    public ResponseEntity<List<Flea>> getFleasByCatId(@PathVariable Long id) {
        List<Flea> fleas = fleaService.getAllByCatId(id);
        return ResponseEntity.ok(fleas);
    }

    @GetMapping("/getAllByName/{name}")
    public ResponseEntity<List<Flea>> getAllFleasByName(@PathVariable String name) {
        List<Flea> fleas = fleaService.getAllByName(name);
        return ResponseEntity.ok(fleas);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Flea>> getAllFleas() {
        List<Flea> fleas = fleaService.getAll();
        return ResponseEntity.ok(fleas);
    }

    @PostMapping("/create")
    public ResponseEntity<Flea> createFlea(@RequestBody Flea flea) {
        Flea newFlea = fleaService.save(flea);
        return ResponseEntity.ok(newFlea);
    }

    @PutMapping("/update")
    public ResponseEntity<Flea> updateFlea(@RequestBody Flea flea) {
        Flea updatedFlea = fleaService.update(flea);
        return ResponseEntity.ok(updatedFlea);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteFlea(@PathVariable Long id) {
        fleaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteByEntity")
    public ResponseEntity<Void> deleteFleaByEntity(@RequestBody Flea flea) {
        fleaService.deleteByEntity(flea);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllFleas() {
        fleaService.deleteAll();
        return ResponseEntity.ok().build();
    }
}