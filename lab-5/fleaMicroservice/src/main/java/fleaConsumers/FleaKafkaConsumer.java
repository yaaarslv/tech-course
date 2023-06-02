package fleaConsumers;

import entities.Flea;
import fleaServices.FleaService;
import fleaWrappers.FleaDataWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FleaKafkaConsumer {
    private final FleaService fleaService;

    private ResponseEntity<List<Flea>> result;

    @Autowired
    public FleaKafkaConsumer(FleaService fleaService) {
        this.fleaService = fleaService;
    }

    @KafkaListener(topics = "flea_topic", groupId = "flea_group")
    public void listen(ConsumerRecord<String, FleaDataWrapper> record) {
        String key = record.key();
        FleaDataWrapper fleaData = record.value();

        switch (key) {
            case "getFleaById" -> processGetFleaByIdMessage(fleaData);
            case "getFleasByOwnerId" -> processGetFleasByCatIdMessage(fleaData);
            case "getFleasByName" -> processGetFleasByNameMessage(fleaData);
            case "getAllFleas" -> processGetAllFleasMessage();
            case "createFlea" -> processCreateFleaMessage(fleaData);
            case "updateFlea" -> processUpdateFleaMessage(fleaData);
            case "deleteFleaById" -> processDeleteFleaByIdMessage(fleaData);
            case "deleteFleaByEntity" -> processDeleteFleaByEntityMessage(fleaData);
            case "deleteAllFleas" -> processDeleteAllFleasMessage();
        }
    }

    public ResponseEntity<List<Flea>> getResult() {
        return result;
    }

    private void processGetFleaByIdMessage(FleaDataWrapper fleaData) {
        Long id = fleaData.getId();
        Optional<Flea> flea = fleaService.getById(id);
        if (flea.get() != null) {
            result = ResponseEntity.ok(List.of(flea.get()));
        } else {
            result = ResponseEntity.notFound().build();
        }
    }

    private void processGetFleasByCatIdMessage(FleaDataWrapper fleaData) {
        Long id = fleaData.getCatId();
        List<Flea> fleas = fleaService.getAllByCatId(id);
        if (fleas.stream().allMatch(Objects::nonNull)) {
            result = ResponseEntity.ok(fleas);
        } else {
            result = ResponseEntity.notFound().build();
        }
    }

    private void processGetFleasByNameMessage(FleaDataWrapper fleaData) {
        String name = fleaData.getName();
        List<Flea> fleas = fleaService.getAllByName(name);
        result = ResponseEntity.ok(fleas);
    }

    private void processGetAllFleasMessage() {
        List<Flea> fleas = fleaService.getAll();
        result = ResponseEntity.ok(fleas);
    }

    private void processCreateFleaMessage(FleaDataWrapper fleaData) {
        Flea flea = fleaData.fromWrapper();
        Flea createdFlea = fleaService.save(flea);
        result = ResponseEntity.ok(List.of(createdFlea));
    }

    private void processUpdateFleaMessage(FleaDataWrapper fleaData) {
        Flea flea = fleaData.fromWrapper();
        Flea updatedFlea = fleaService.update(flea);
        result = ResponseEntity.ok(List.of(updatedFlea));
    }

    private void processDeleteFleaByIdMessage(FleaDataWrapper fleaData) {
        Long id = fleaData.getId();
        fleaService.deleteById(id);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteFleaByEntityMessage(FleaDataWrapper fleaData) {
        Flea flea = fleaData.fromWrapper();
        fleaService.deleteByEntity(flea);
        result = ResponseEntity.ok().build();
    }

    private void processDeleteAllFleasMessage() {
        fleaService.deleteAll();
        result = ResponseEntity.ok().build();
    }
}
