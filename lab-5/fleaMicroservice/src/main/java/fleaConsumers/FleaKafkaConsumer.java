package fleaConsumers;

import fleaWrappers.FleaDataWrapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Properties;

@Service
public class FleaKafkaConsumer {
    private final Consumer<String, FleaDataWrapper> consumer;

    public FleaKafkaConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "flea_group");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "fleaSerialization.FleaDataWrapperDeserializer");
        this.consumer = new KafkaConsumer<>(properties);
    }

    @KafkaListener(topics = "fleas_topic", groupId = "flea_group")
    public void startConsuming() {
        while (true) {
            ConsumerRecords<String, FleaDataWrapper> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, FleaDataWrapper> record : records) {
                FleaDataWrapper fleaDataWrapper = record.value();
                System.out.println("Received flea data:");
                System.out.println("Id: " + fleaDataWrapper.getId());
                System.out.println("Name: " + fleaDataWrapper.getName());
                System.out.println("CatId: " + fleaDataWrapper.getCatId());
            }
        }
    }
}
