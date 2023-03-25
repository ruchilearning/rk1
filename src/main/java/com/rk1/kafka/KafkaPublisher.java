package com.rk1.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        try {
            kafkaTemplate.send("tutorialspoint", msg);
        } catch (Exception ex) {
            System.out.printf("Error");
        }
    }
}
