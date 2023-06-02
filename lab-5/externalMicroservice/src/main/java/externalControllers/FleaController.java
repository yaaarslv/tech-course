package externalControllers;

import entities.Flea;
import fleaConsumers.FleaKafkaConsumer;
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

import java.util.List;

@RestController
@RequestMapping("/fleas")
public class FleaController {
    private final FleaKafkaProducer producer;

    private final FleaKafkaConsumer consumer;

    @Autowired
    public FleaController(FleaKafkaProducer producer, FleaKafkaConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<List<Flea>> getFleaById(@PathVariable Long id) {
        producer.sendGetFleaByIdMessage(id);
        return consumer.getResult();
    }

    @GetMapping("/getByCatId/{id}")
    public ResponseEntity<List<Flea>> getFleasByCatId(@PathVariable Long id) {
        producer.sendGetFleasByCatIdMessage(id);
        return consumer.getResult();
    }

    @GetMapping("/getAllByName/{name}")
    public ResponseEntity<List<Flea>> getAllFleasByName(@PathVariable String name) {
        producer.sendGetFleasByNameMessage(name);
        return consumer.getResult();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Flea>> getAllFleas() {
        producer.sendGetAllFleasMessage();
        return consumer.getResult();
    }

    @PostMapping("/create")
    public ResponseEntity<List<Flea>> createFlea(@RequestBody Flea flea) {
        producer.sendCreateFleaMessage(flea);
        return consumer.getResult();
    }

    @PutMapping("/update")
    public ResponseEntity<List<Flea>> updateFlea(@RequestBody Flea flea) {
        producer.sendUpdateFleaMessage(flea);
        return consumer.getResult();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteFlea(@PathVariable Long id) {
        producer.sendDeleteFleaByIdMessage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByEntity")
    public ResponseEntity<Void> deleteFleaByEntity(@RequestBody Flea flea) {
        producer.sendDeleteFleaByEntityMessage(flea);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllFleas() {
        producer.sendDeleteAllFleasMessage();
        return ResponseEntity.ok().build();
    }
}