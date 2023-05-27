package externalControllers;

import entities.Flea;
import fleaProducers.FleaKafkaProducer;
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

    private final FleaKafkaProducer producer;

    @Autowired
    public FleaController(FleaService fleaService, FleaKafkaProducer producer) {
        this.producer = producer;
        this.fleaService = fleaService;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Flea> getFleaById(@PathVariable Long id) {
        Optional<Flea> flea = fleaService.getById(id);
        producer.sendFleaData(flea.get().getId(), flea.get().getName(), flea.get().getCatId());
        return ResponseEntity.ok(flea.get());
    }

    @GetMapping("/getByCatId/{id}")
    public ResponseEntity<List<Flea>> getFleasByCatId(@PathVariable Long id) {
        List<Flea> fleas = fleaService.getAllByCatId(id);
        fleas.forEach(flea -> producer.sendFleaData(flea.getId(), flea.getName(), flea.getCatId()));
        return ResponseEntity.ok(fleas);
    }

    @GetMapping("/getAllByName/{name}")
    public ResponseEntity<List<Flea>> getAllFleasByName(@PathVariable String name) {
        List<Flea> fleas = fleaService.getAllByName(name);
        fleas.forEach(flea -> producer.sendFleaData(flea.getId(), flea.getName(), flea.getCatId()));
        return ResponseEntity.ok(fleas);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Flea>> getAllFleas() {
        List<Flea> fleas = fleaService.getAll();
        fleas.forEach(flea -> producer.sendFleaData(flea.getId(), flea.getName(), flea.getCatId()));
        return ResponseEntity.ok(fleas);
    }

    @PostMapping("/create")
    public ResponseEntity<Flea> createFlea(@RequestBody Flea flea) {
        Flea newFlea = fleaService.save(flea);
        producer.sendFleaData(newFlea.getId(), newFlea.getName(), newFlea.getCatId());
        return ResponseEntity.ok(newFlea);
    }

    @PutMapping("/update")
    public ResponseEntity<Flea> updateFlea(@RequestBody Flea flea) {
        Flea updatedFlea = fleaService.update(flea);
        producer.sendFleaData(updatedFlea.getId(), updatedFlea.getName(), updatedFlea.getCatId());
        return ResponseEntity.ok(updatedFlea);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteFlea(@PathVariable Long id) {
        Optional<Flea> flea = fleaService.getById(id);
        producer.sendFleaData(flea.get().getId(), flea.get().getName(), flea.get().getCatId());
        fleaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteByEntity")
    public ResponseEntity<Void> deleteFleaByEntity(@RequestBody Flea flea) {
        producer.sendFleaData(flea.getId(), flea.getName(), flea.getCatId());
        fleaService.deleteByEntity(flea);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllFleas() {
        List<Flea> fleas = fleaService.getAll();
        fleas.forEach(flea -> producer.sendFleaData(flea.getId(), flea.getName(), flea.getCatId()));
        fleaService.deleteAll();
        return ResponseEntity.ok().build();
    }
}