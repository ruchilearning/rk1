package com.rk1;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MyComponentTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setup() {
        WireMock.configureFor("localhost", wireMockServer.port());
        wireMockServer.start();
    }

    @Test
    public void testMyEndpoint() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/hello2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"response\":\"Mocked response\"}")));

        webTestClient.get()
                .uri("/my-endpoint")
                .exchange()
                .expectStatus().isOk();
//                .expectBody(MyResponse.class)
//                .isEqualTo(new MyResponse("Mocked response"));

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/my-endpoint")));
    }

}
