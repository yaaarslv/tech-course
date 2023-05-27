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
import ownerProducer.OwnerKafkaProducer;
import ownerServices.OwnerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
@PreAuthorize("hasRole('ADMIN')")
public class OwnerController {
    private final OwnerService ownerService;

    private final OwnerKafkaProducer producer;

    @Autowired
    public OwnerController(OwnerService ownerService, OwnerKafkaProducer producer) {
        this.ownerService = ownerService;
        this.producer = producer;
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.getById(id);
        producer.sendOwnerData(owner.get().getId(), owner.get().getName(), owner.get().getBirthdate());
        return ResponseEntity.ok(owner.get());
    }

    @GetMapping("/getAllByName/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> getAllOwnersByName(@PathVariable String name) {
        List<Owner> owners = ownerService.getAllByName(name);
        owners.forEach(owner -> producer.sendOwnerData(owner.getId(), owner.getName(), owner.getBirthdate()));
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> owners = ownerService.getAll();
        owners.forEach(owner -> producer.sendOwnerData(owner.getId(), owner.getName(), owner.getBirthdate()));
        return ResponseEntity.ok(owners);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner createdOwner = ownerService.save(owner);
        producer.sendOwnerData(owner.getId(), owner.getName(), owner.getBirthdate());
        return ResponseEntity.ok(createdOwner);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner) {
        Owner updatedOwner = ownerService.update(owner);
        producer.sendOwnerData(updatedOwner.getId(), updatedOwner.getName(), updatedOwner.getBirthdate());
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.getById(id);
        producer.sendOwnerData(owner.get().getId(), owner.get().getName(), owner.get().getBirthdate());
        ownerService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteByEntity")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOwnerByEntity(@RequestBody Owner owner) {
        producer.sendOwnerData(owner.getId(), owner.getName(), owner.getBirthdate());
        ownerService.deleteByEntity(owner);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAllOwners() {
        List<Owner> owners = ownerService.getAll();
        owners.forEach(owner -> producer.sendOwnerData(owner.getId(), owner.getName(), owner.getBirthdate()));
        ownerService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
