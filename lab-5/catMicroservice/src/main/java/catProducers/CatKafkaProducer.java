package catProducers;

import catWrappers.CatDataWrapper;
import entities.Cat;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatKafkaProducer {
    private final Producer<String, CatDataWrapper> producer;

    private final String topic;

    @Autowired
    public CatKafkaProducer(KafkaProducer<String, CatDataWrapper> producer) {
        this.producer = producer;
        this.topic = "cat_topic";
    }

    public void sendGetCatByIdMessage(Long id) {
        String key = "getCatById";
        CatDataWrapper catData = new CatDataWrapper(id, null, null, null, null, 0, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendGetCatsByOwnerIdMessage(Long id) {
        String key = "getCatsByOwnerId";
        CatDataWrapper catData = new CatDataWrapper(0L, null, null, null, null, id, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendGetCatsByNameMessage(String name) {
        String key = "getCatsByName";
        CatDataWrapper catData = new CatDataWrapper(0L, name, null, null, null, 0, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendGetAllCatsMessage() {
        String key = "getAllCats";
        CatDataWrapper catData = new CatDataWrapper(0L, null, null, null, null, 0, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendCreateCatMessage(Cat cat) {
        String key = "createCat";
        CatDataWrapper catData = CatDataWrapper.toWrapper(cat);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendUpdateCatMessage(Cat cat) {
        String key = "updateCat";
        CatDataWrapper catData = CatDataWrapper.toWrapper(cat);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendDeleteCatByIdMessage(Long id) {
        String key = "deleteCatById";
        CatDataWrapper catData = new CatDataWrapper(id, null, null, null, null, 0, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendDeleteCatByEntityMessage(Cat cat) {
        String key = "deleteCatByEntity";
        CatDataWrapper catData = CatDataWrapper.toWrapper(cat);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }

    public void sendDeleteAllCatsMessage() {
        String key = "deleteAllCats";
        CatDataWrapper catData = new CatDataWrapper(0L, null, null, null, null, 0, 0);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, key, catData);
        producer.send(record);
    }
}
