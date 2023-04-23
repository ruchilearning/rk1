package com.rk1.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyKafkaListener {

    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "test1", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("Received message: " + record.value());
        kafkaProducer.sendMessage("test2", record.value());
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "test3", groupId = "my-group2", containerFactory = "kafkaListenerContainerFactory")
    public void onAvroMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        System.out.println("Received message: " + record.value());
        kafkaProducer.sendMessage("test2", record.value());
        acknowledgment.acknowledge();
    }
}
