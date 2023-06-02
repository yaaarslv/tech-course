package ownerConsumer;

import entities.Owner;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ownerServices.OwnerService;
import ownerWrappers.OwnerDataWrapper;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerKafkaConsumer {
    private final OwnerService ownerService;

    private ResponseEntity<List<Owner>> result;

    @Autowired
    public OwnerKafkaConsumer(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @KafkaListener(topics = "owner_topic", groupId = "owner_group")
    public void listen(ConsumerRecord<String, OwnerDataWrapper> record) {
        String key = record.key();
        OwnerDataWrapper ownerData = record.value();

        switch (key) {
            case "getOwnerById" -> processGetOwnerByIdMessage(ownerData);
            case "getOwnersByName" -> processGetOwnersByNameMessage(ownerData);
            case "getAllOwners" -> processGetAllOwnersMessage();
            case "createOwner" -> processCreateOwnerMessage(ownerData);
            case "updateOwner" -> processUpdateOwnerMessage(ownerData);
            case "deleteOwnerById" -> processDeleteOwnerByIdMessage(ownerData);
            case "deleteOwnerByEntity" -> processDeleteOwnerByEntityMessage(ownerData);
            case "deleteAllOwners" -> processDeleteAllOwnersMessage();
        }
    }

    public ResponseEntity<List<Owner>> getResult() {
        return result;
    }

    private void processGetOwnerByIdMessage(OwnerDataWrapper ownerData) {
        Long id = ownerData.getId();
        Optional<Owner> owner = ownerService.getById(id);
        if (owner.get() != null) {
            result = ResponseEntity.ok(List.of(owner.get()));
        } else {
            result = ResponseEntity.notFound().build();
        }
    }

    private void processGetOwnersByNameMessage(OwnerDataWrapper ownerData) {
        String name = ownerData.getName();
        List<Owner> owners = ownerService.getAllByName(name);
        result = ResponseEntity.ok(owners);
    }

    private void processGetAllOwnersMessage() {
        List<Owner> owners = ownerService.getAll();
        result = ResponseEntity.ok(owners);
    }

    private void processCreateOwnerMessage(OwnerDataWrapper ownerData) {
        Owner owner = ownerData.fromWrapper();
        Owner createdOwner = ownerService.save(owner);
        result = ResponseEntity.ok(List.of(createdOwner));
    }

    private void processUpdateOwnerMessage(OwnerDataWrapper ownerData) {
        Owner owner = ownerData.fromWrapper();
        Owner updatedOwner = ownerService.update(owner);
        result = ResponseEntity.ok(List.of(updatedOwner));
    }

    private void processDeleteOwnerByIdMessage(OwnerDataWrapper ownerData) {
        Long id = ownerData.getId();
        ownerService.deleteById(id);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteOwnerByEntityMessage(OwnerDataWrapper ownerData) {
        Owner owner = ownerData.fromWrapper();
        ownerService.deleteByEntity(owner);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteAllOwnersMessage() {
        ownerService.deleteAll();
        result = ResponseEntity.ok().build();
    }
}
