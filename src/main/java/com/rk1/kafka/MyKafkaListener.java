package com.rk1.kafka;

import com.rk1.service.MyListenerService;
import com.rk5.avro01.Avro01;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyKafkaListener {

    private final KafkaProducer kafkaProducer;
    private final MyListenerService myListenerService;

    @KafkaListener(topics = "test1", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void onMessage(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("Received message: {}", record.value());
        kafkaProducer.sendMessage("test2", record.value());
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "rk-avro01", groupId = "my-group-avro", containerFactory = "kafkaListenerContainerFactoryAvro")
    public void onAvroMessage(ConsumerRecord<String, Avro01> record, Acknowledgment acknowledgment) {
        Avro01 value = record.value();
        log.info("Received key: {}, Received message: {}", record.key(), value);
        myListenerService.processKafkaMessage(value);
        acknowledgment.acknowledge();
    }

}
