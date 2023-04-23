package com.rk1.service;

import com.rk1.kafka.KafkaProducer;
import com.rk1.repository.HelloRepository;
import com.rk5.avro01.Avro01;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;
    private final KafkaProducer kafkaProducer;


    public Mono<HelloRepository.HelloResponse> callHello()
    {
        return helloRepository.getExample();
    }

    public Avro01 callKafka() {
        Avro01 avro01 = Avro01.newBuilder().setFullName(UUID.randomUUID().toString()).setActive(true).build();
        kafkaProducer.sendMessage("test1", UUID.randomUUID().toString());
        return avro01;
    }

    public Avro01 callAvroKafka() {
        Avro01 avro01 = Avro01.newBuilder().setUuid(UUID.randomUUID().toString())
                .setFullName("Donald Duck").setActive(true).build();
        kafkaProducer.sendMessageAvro("rk-avro01", avro01);
        return avro01;
    }
}