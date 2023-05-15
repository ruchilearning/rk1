package com.rk1.controller;

import com.rk1.repository.HelloRepository;
import com.rk1.repository.entity.User;
import com.rk1.service.HelloService;
import com.rk5.avro01.Avro01;
import lombok.RequiredArgsConstructor;
import org.apache.avro.data.Json;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="hello")
public class HelloController {

    private final HelloService helloService;

    @GetMapping(value="/hello")
    @ResponseBody
    public Mono<HelloRepository.HelloResponse> bootUp()
    {
        return helloService.callHello();

    }

    @GetMapping(value="/kafka")
    @ResponseBody
    public User kafka()
    {
        Avro01 avro01 = helloService.callKafka();
        User user = User.builder()
                .firstName(avro01.getFirstName())
                .lastName(avro01.getLastName())
                .emailId(avro01.getUuid())
                .active(avro01.getActive()).build();
        return user;

    }

    @GetMapping(value="/kafkaAvro/{name}")
    @ResponseBody
    public User kafkaAvro(@PathVariable("name") String name,
                            @RequestParam(value = "active", required = false) Boolean status)
    {

        Avro01 avro01 = helloService.callAvroKafka(name, status);
        User user = User.builder()
                .firstName(avro01.getFirstName())
                .lastName(avro01.getLastName())
                .emailId(avro01.getUuid())
                .active(avro01.getActive()).build();
        return user;
    }

    @GetMapping(value="/kafkaAvroTopicRecord")
    @ResponseBody
    public Object kafkaAvroTopicRecord()
    {
        return helloService.callAvroKafkaTopicRecord();

    }

    @GetMapping("/flux")
    public Flux<Integer> flux(){

        return Flux.just(1,2,3)
                .log();
    }

    @GetMapping("/mono")
    public Mono<String> helloWorldMono(){

        return Mono.just("hello-world")
                .log();

    }


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> stream(){

        return Flux.interval(Duration.ofSeconds(1))
                .log();

    }
}