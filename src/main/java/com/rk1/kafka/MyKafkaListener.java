package com.rk1.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class MyKafkaListener {
    @KafkaListener(topics = "tutorialspoint", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received Messasge in group - group-id: " + message);
    }
}
