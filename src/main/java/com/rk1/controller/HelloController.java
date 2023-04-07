package com.rk1.controller;

import com.rk1.configs.KafkaConfigProperties;
import com.rk1.repository.HelloRepository;
import com.rk1.service.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class HelloController {

    private final KafkaConfigProperties kafkaConfigProperties;
    private final HelloService helloService;

    @GetMapping(value="/hello")
    @ResponseBody
    public Mono<HelloRepository.HelloResponse> bootup()
    {

//        return "SpringBoot is up and running";
//        return kafkaConfigProperties.toString();
        return helloService.callHello();

    }
}