package catConsumers;

import catWrappers.CatDataWrapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Service
public class CatKafkaConsumer {
    private final Consumer<String, CatDataWrapper> consumer;

    public CatKafkaConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "cat_group");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "catSerialization.CatDataWrapperDeserializer");
        this.consumer = new KafkaConsumer<>(properties);
    }

    @KafkaListener(topics = "cats_topic", groupId = "cat_group")
    public void startConsuming() {
        while (true) {
            ConsumerRecords<String, CatDataWrapper> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, CatDataWrapper> record : records) {
                CatDataWrapper catDataWrapper = record.value();
                System.out.println("Received cat data:");
                System.out.println("Id: " + catDataWrapper.getId());
                System.out.println("Name: " + catDataWrapper.getName());
                System.out.println("Birthdate: " + catDataWrapper.getBirthdate());
                System.out.println("Breed: " + catDataWrapper.getBreed());
                System.out.println("Color: " + catDataWrapper.getColor());
                System.out.println("OwnerId: " + catDataWrapper.getOwnerId());
                System.out.println("TailLength: " + catDataWrapper.getTailLength());
            }
        }
    }
}
