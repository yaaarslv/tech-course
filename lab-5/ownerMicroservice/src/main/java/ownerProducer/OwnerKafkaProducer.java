package ownerProducer;

import entities.Owner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ownerWrappers.OwnerDataWrapper;

@Service
public class OwnerKafkaProducer {
    private final Producer<String, OwnerDataWrapper> producer;

    private final String topic;

    @Autowired
    public OwnerKafkaProducer(KafkaProducer<String, OwnerDataWrapper> producer) {
        this.producer = producer;
        this.topic = "owner_topic";
    }

    public void sendGetOwnerByIdMessage(Long id) {
        String key = "getOwnerById";
        OwnerDataWrapper ownerData = new OwnerDataWrapper(id, null, null);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendGetOwnersByNameMessage(String name) {
        String key = "getOwnersByName";
        OwnerDataWrapper ownerData = new OwnerDataWrapper(0L, name, null);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendGetAllOwnersMessage() {
        String key = "getAllOwners";
        OwnerDataWrapper ownerData = new OwnerDataWrapper(0L, null, null);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendCreateOwnerMessage(Owner owner) {
        String key = "createOwner";
        OwnerDataWrapper ownerData = OwnerDataWrapper.toWrapper(owner);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendUpdateOwnerMessage(Owner owner) {
        String key = "updateOwner";
        OwnerDataWrapper ownerData = OwnerDataWrapper.toWrapper(owner);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendDeleteOwnerByIdMessage(Long id) {
        String key = "deleteOwnerById";
        OwnerDataWrapper ownerData = new OwnerDataWrapper(id, null, null);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendDeleteOwnerByEntityMessage(Owner owner) {
        String key = "deleteOwnerByEntity";
        OwnerDataWrapper ownerData = OwnerDataWrapper.toWrapper(owner);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }

    public void sendDeleteAllOwnersMessage() {
        String key = "deleteAllOwners";
        OwnerDataWrapper ownerData = new OwnerDataWrapper(0L, null, null);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, key, ownerData);
        producer.send(record);
    }
}
