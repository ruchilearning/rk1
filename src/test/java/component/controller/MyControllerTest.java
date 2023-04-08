package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.rk1.RkApplication;
import com.rk1.repository.HelloRepository;
import configs.MyWireMockConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {RkApplication.class, MyWireMockConfig.class})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestPropertySource(properties = {"api-services.hello.baseUrl=http://localhost:8099"})
@ComponentScan(basePackages = {"com.rk1.configs"})
public class MyControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    @Qualifier("wireMyMockServerEndpoint")
    private WireMockServer wireMyMockServerEndpoint;


    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.wireMyMockServerEndpoint.start();
    }

    @AfterEach
    public void tearDown() {
        this.wireMyMockServerEndpoint.stop();
    }

    @Test
    public void testMyController() throws JsonProcessingException {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);

        this.wireMyMockServerEndpoint.stubFor(WireMock.get(WireMock.urlEqualTo("/api/two"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        String responseBody = webClient.get().uri("http://localhost:" + port + "/hello/hello")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(json)
                .returnResult()
                .getResponseBody();

        Assertions.assertThat(responseBody).isEqualTo(json);
        wireMyMockServerEndpoint.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/two")));
    }
}
