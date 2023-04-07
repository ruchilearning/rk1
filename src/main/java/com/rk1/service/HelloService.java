package com.rk1.service;

import com.rk1.configs.KafkaConfigProperties;
import com.rk1.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class HelloService {

    private final KafkaConfigProperties kafkaConfigProperties;
    private final HelloRepository helloRepository;


    public Mono<HelloRepository.HelloResponse> callHello()
    {
        return helloRepository.getExample();
    }
}