package ownerConsumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ownerWrappers.OwnerDataWrapper;

import java.time.Duration;
import java.util.Properties;

@Service
public class OwnerKafkaConsumer {
    private final Consumer<String, OwnerDataWrapper> consumer;

    public OwnerKafkaConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "owner_group");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "ownerSerialization.OwnerDataWrapperDeserializer");
        this.consumer = new KafkaConsumer<>(properties);
    }

    @KafkaListener(topics = "owners_topic", groupId = "owner_group")
    public void startConsuming() {
        while (true) {
            ConsumerRecords<String, OwnerDataWrapper> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, OwnerDataWrapper> record : records) {
                OwnerDataWrapper ownerDataWrapper = record.value();
                System.out.println("Received owner data:");
                System.out.println("Id: " + ownerDataWrapper.getId());
                System.out.println("Name: " + ownerDataWrapper.getName());
                System.out.println("Birthdate: " + ownerDataWrapper.getBirthdate());
            }
        }
    }
}
