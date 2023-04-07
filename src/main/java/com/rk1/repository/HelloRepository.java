package com.rk1.repository;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class HelloRepository {

    private final WebClient webClient;
    @Value("${api-services.hello.baseUrl}")
    private String baseUrl;

    @Value("${api-services.hello.api2}")
    private String path;

    public HelloRepository(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<HelloResponse> getExample() {
        return webClient.get()
                .uri(baseUrl + path)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HelloResponse.class);
    }

    @Data
    public static class HelloResponse {
        private String name;
        private int age;
        private String email;

    }
}
