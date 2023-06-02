package fleaProducers;

import entities.Flea;
import fleaWrappers.FleaDataWrapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FleaKafkaProducer {
    private final Producer<String, FleaDataWrapper> producer;

    private final String topic;

    @Autowired
    public FleaKafkaProducer(KafkaProducer<String, FleaDataWrapper> producer) {
        this.producer = producer;
        this.topic = "flea_topic";
    }

    public void sendGetFleaByIdMessage(Long id) {
        String key = "getFleaById";
        FleaDataWrapper fleaData = new FleaDataWrapper(id, null, 0L);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendGetFleasByCatIdMessage(Long id) {
        String key = "getFleasByCatId";
        FleaDataWrapper fleaData = new FleaDataWrapper(0L, null,  id);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendGetFleasByNameMessage(String name) {
        String key = "getFleasByName";
        FleaDataWrapper fleaData = new FleaDataWrapper(0L, name, 0L);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendGetAllFleasMessage() {
        String key = "getAllFleas";
        FleaDataWrapper fleaData = new FleaDataWrapper(0L, null, 0L);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendCreateFleaMessage(Flea flea) {
        String key = "createFlea";
        FleaDataWrapper fleaData = FleaDataWrapper.toWrapper(flea);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendUpdateFleaMessage(Flea flea) {
        String key = "updateFlea";
        FleaDataWrapper fleaData = FleaDataWrapper.toWrapper(flea);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendDeleteFleaByIdMessage(Long id) {
        String key = "deleteFleaById";
        FleaDataWrapper fleaData = new FleaDataWrapper(id, null, 0L);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendDeleteFleaByEntityMessage(Flea flea) {
        String key = "deleteFleaByEntity";
        FleaDataWrapper fleaData = FleaDataWrapper.toWrapper(flea);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }

    public void sendDeleteAllFleasMessage() {
        String key = "deleteAllFleas";
        FleaDataWrapper fleaData = new FleaDataWrapper(0L, null, 0L);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, key, fleaData);
        producer.send(record);
    }
}
