package externalControllers;

import entities.Owner;
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
import ownerConsumer.OwnerKafkaConsumer;
import ownerProducer.OwnerKafkaProducer;

import java.util.List;

@RestController
@RequestMapping("/owners")
@PreAuthorize("hasRole('ADMIN')")
public class OwnerController {
    private final OwnerKafkaProducer producer;

    private final OwnerKafkaConsumer consumer;

    @Autowired
    public OwnerController(OwnerKafkaProducer producer, OwnerKafkaConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> getOwnerById(@PathVariable Long id) {
        producer.sendGetOwnerByIdMessage(id);
        return consumer.getResult();
    }

    @GetMapping("/getAllByName/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> getAllOwnersByName(@PathVariable String name) {
        producer.sendGetOwnersByNameMessage(name);
        return consumer.getResult();
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> getAllOwners() {
        producer.sendGetAllOwnersMessage();
        return consumer.getResult();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> createOwner(@RequestBody Owner owner) {
        producer.sendCreateOwnerMessage(owner);
        return consumer.getResult();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> updateOwner(@RequestBody Owner owner) {
        producer.sendUpdateOwnerMessage(owner);
        return consumer.getResult();
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        producer.sendDeleteOwnerByIdMessage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByEntity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwnerByEntity(@RequestBody Owner owner) {
        producer.sendDeleteOwnerByEntityMessage(owner);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllOwners() {
        producer.sendDeleteAllOwnersMessage();
        return ResponseEntity.ok().build();
    }
}
