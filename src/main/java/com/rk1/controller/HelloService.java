package com.rk1.controller;

import com.rk1.configs.KafkaConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloService {

    private final KafkaConfigProperties kafkaConfigProperties;

    @GetMapping(value="/hello")
    @ResponseBody
    public String bootup()
    {

//        return "SpringBoot is up and running";
        return kafkaConfigProperties.toString();

    }
}