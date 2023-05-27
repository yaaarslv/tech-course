package catProducers;

import catWrappers.CatDataWrapper;
import models.CatColor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.Properties;

@Service
public class CatKafkaProducer {
    private final Producer<String, CatDataWrapper> producer;

    private final String topic;

    public CatKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "catSerialization.CatDataWrapperSerializer");
        this.producer = new KafkaProducer<>(properties);
        this.topic = "cats_topic";
    }

    public void sendCatData(Long id, String name, Date birthdate, String breed, CatColor color, long ownerId, int tailLength) {
        CatDataWrapper catDataWrapper = new CatDataWrapper(id, name, birthdate, breed, color, ownerId, tailLength);
        ProducerRecord<String, CatDataWrapper> record = new ProducerRecord<>(topic, "cat_id", catDataWrapper);
        producer.send(record);
    }
}
