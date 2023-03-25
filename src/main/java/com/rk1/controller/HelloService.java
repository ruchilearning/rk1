package com.rk1.controller;

import com.rk1.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {

    @GetMapping(value="/hello")
    @ResponseBody
    public String bootup()
    {

        return "SpringBoot is up and running";

    }
}