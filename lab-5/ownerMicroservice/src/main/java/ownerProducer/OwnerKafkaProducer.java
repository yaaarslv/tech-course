package ownerProducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ownerWrappers.OwnerDataWrapper;

import java.sql.Date;
import java.util.Properties;

@Service
public class OwnerKafkaProducer {
    private final Producer<String, OwnerDataWrapper> producer;

    private final String topic;

    public OwnerKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "ownerSerialization.OwnerDataWrapperSerializer");
        this.producer = new KafkaProducer<>(properties);
        this.topic = "owners_topic";
    }

    public void sendOwnerData(Long id, String name, Date birthdate) {
        OwnerDataWrapper ownerDataWrapper = new OwnerDataWrapper(id, name, birthdate);
        ProducerRecord<String, OwnerDataWrapper> record = new ProducerRecord<>(topic, "owner_id", ownerDataWrapper);
        producer.send(record);
    }
}
