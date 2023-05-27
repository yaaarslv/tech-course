package fleaProducers;

import fleaWrappers.FleaDataWrapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class FleaKafkaProducer {
    private final Producer<String, FleaDataWrapper> producer;

    private final String topic;

    public FleaKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "fleaSerialization.FleaDataWrapperSerializer");
        this.producer = new KafkaProducer<>(properties);
        this.topic = "fleas_topic";
    }

    public void sendFleaData(Long id, String name, Long catId) {
        FleaDataWrapper fleaDataWrapper = new FleaDataWrapper(id, name, catId);
        ProducerRecord<String, FleaDataWrapper> record = new ProducerRecord<>(topic, "flea_id", fleaDataWrapper);
        producer.send(record);
    }
}
