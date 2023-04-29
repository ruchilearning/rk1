package com.rk1.service;

import com.rk1.repository.entity.User;
import com.rk1.repository.entity.UserRepository;
import com.rk1.kafka.KafkaProducer;
import com.rk1.repository.HelloRepository;
import com.rk5.avro01.Avro01;
import com.rk5.user.UserCreated;
import com.rk5.user.UserUpdated;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;
    private final KafkaProducer kafkaProducer;
    private final UserRepository userRepository;


    public Mono<HelloRepository.HelloResponse> callHello()
    {
        return helloRepository.getExample();
    }

    public Avro01 callKafka() {
        Avro01 avro01 = Avro01.newBuilder().setFirstName(UUID.randomUUID().toString()).setActive(true).build();
        kafkaProducer.sendMessage("test1", UUID.randomUUID().toString());
        return avro01;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Retry(name = "myRetry")
    public Avro01 callAvroKafka(String firstName, boolean status) {
        Avro01 avro01 = Avro01.newBuilder().setUuid(UUID.randomUUID().toString())
                .setFirstName(firstName)
                .setLastName("Duck").setActive(status).build();

        User user = User.builder()
                .firstName(avro01.getFirstName())
                .lastName(avro01.getLastName()).active(avro01.getActive()).build();
        userRepository.save(user);
        kafkaProducer.sendMessageAvro("rk-avro01", avro01);
        return avro01;
    }


    public Object callAvroKafkaTopicRecord() {
        UserCreated userCreated = new UserCreated("1000", "userFirstName", "UserLastName","userOne@email.com");

        User user = User.builder()
                .firstName(userCreated.getFirstName())
                .lastName(userCreated.getLastName())
                .emailId(userCreated.getEmailId()).build();

        userRepository.save(user);
        kafkaProducer.sendMessageAvroTopicRecord("rk-KafkaTopicRecord", userCreated);

        UserUpdated userUpdated = new UserUpdated("1000", "Updated UserFirstName",
                "Updated UserLastName", "userOne@email.com",
                Instant.now().toEpochMilli());

        user = User.builder()
                .firstName(userUpdated.getFirstName())
                .lastName(userUpdated.getLastName()).build();

        userRepository.save(user);
        kafkaProducer.sendMessageAvroTopicRecord("rk-KafkaTopicRecord", userUpdated);
        return userCreated.toString() + userCreated.toString();
    }

}