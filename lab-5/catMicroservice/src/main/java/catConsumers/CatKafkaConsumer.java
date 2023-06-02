package catConsumers;

import catServices.CatService;
import catWrappers.CatDataWrapper;
import entities.Cat;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CatKafkaConsumer {
    private final CatService catService;

    private ResponseEntity<List<Cat>> result;

    @Autowired
    public CatKafkaConsumer(CatService catService) {
        this.catService = catService;
    }

    @KafkaListener(topics = "cat_topic", groupId = "cat_group")
    public void listen(ConsumerRecord<String, CatDataWrapper> record) {
        String key = record.key();
        CatDataWrapper catData = record.value();

        switch (key) {
            case "getCatById" -> processGetCatByIdMessage(catData);
            case "getCatsByOwnerId" -> processGetCatsByOwnerIdMessage(catData);
            case "getCatsByName" -> processGetCatsByNameMessage(catData);
            case "getAllCats" -> processGetAllCatsMessage();
            case "createCat" -> processCreateCatMessage(catData);
            case "updateCat" -> processUpdateCatMessage(catData);
            case "deleteCatById" -> processDeleteCatByIdMessage(catData);
            case "deleteCatByEntity" -> processDeleteCatByEntityMessage(catData);
            case "deleteAllCats" -> processDeleteAllCatsMessage();
        }
    }

    public ResponseEntity<List<Cat>> getResult() {
        return result;
    }

    private void processGetCatByIdMessage(CatDataWrapper catData) {
        Long id = catData.getId();
        Optional<Cat> cat = catService.getById(id);
        if (cat.get() != null) {
            result = ResponseEntity.ok(List.of(cat.get()));
        } else {
            result = ResponseEntity.notFound().build();
        }
    }

    private void processGetCatsByOwnerIdMessage(CatDataWrapper catData) {
        Long id = catData.getOwnerId();
        List<Cat> cats = catService.getAllByOwnerId(id);
        if (cats.stream().allMatch(Objects::nonNull)) {
            result = ResponseEntity.ok(cats);
        } else {
            result = ResponseEntity.notFound().build();
        }
    }

    private void processGetCatsByNameMessage(CatDataWrapper catData) {
        String name = catData.getName();
        List<Cat> cats = catService.getAllByName(name);
        result = ResponseEntity.ok(cats);
    }

    private void processGetAllCatsMessage() {
        List<Cat> cats = catService.getAll();
        result = ResponseEntity.ok(cats);
    }

    private void processCreateCatMessage(CatDataWrapper catData) {
        Cat cat = catData.fromWrapper();
        Cat createdCat = catService.save(cat);
        result = ResponseEntity.ok(List.of(createdCat));
    }

    private void processUpdateCatMessage(CatDataWrapper catData) {
        Cat cat = catData.fromWrapper();
        Cat updatedCat = catService.update(cat);
        result = ResponseEntity.ok(List.of(updatedCat));
    }

    private void processDeleteCatByIdMessage(CatDataWrapper catData) {
        Long id = catData.getId();
        catService.deleteById(id);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteCatByEntityMessage(CatDataWrapper catData) {
        Cat cat = catData.fromWrapper();
        catService.deleteByEntity(cat);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteAllCatsMessage() {
        catService.deleteAll();
        result = ResponseEntity.ok().build();
    }
}
