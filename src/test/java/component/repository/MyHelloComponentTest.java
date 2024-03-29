package repository;

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
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {RkApplication.class, MyWireMockConfig.class})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestPropertySource(properties = {"api-services.hello.baseUrl=http://localhost:8091"})
@ComponentScan(basePackages = {"com.rk1.configs"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyHelloComponentTest {

    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    @Qualifier("wireMyMockServerEndpoint2")
    private WireMockServer wireMyMockServerEndpoint2;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.wireMyMockServerEndpoint2.start();
    }

    @AfterEach
    public void tearDown() {
        this.wireMyMockServerEndpoint2.stop();
    }


    @Test
    public void testMyEndpoint() throws JsonProcessingException {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);


        Mono<HelloRepository.HelloResponse> helloResponseMono = helloRepository.getExample();
        String expectedJson = objectMapper.writeValueAsString(helloResponseMono.block());


        Assertions.assertThat(expectedJson).isEqualTo(json);
        wireMyMockServerEndpoint2.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/two")));
    }

    @Test
    public void testMyEndpoint2() throws JsonProcessingException {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);

        HelloRepository.HelloResponse helloResponse = helloRepository.getExample_two();
        String expectedJson = objectMapper.writeValueAsString(helloResponse);

        Assertions.assertThat(expectedJson).isEqualTo(json);
    }

    @Test
    public void testMyEndpoint_api_three() throws JsonProcessingException {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);

        this.wireMyMockServerEndpoint2.stubFor(WireMock.get(WireMock.urlEqualTo("/api/three"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        Mono<HelloRepository.HelloResponse> helloResponseMono = helloRepository.getExample_three();
        String expectedJson = objectMapper.writeValueAsString(helloResponseMono.block());


        Assertions.assertThat(expectedJson).isEqualTo(json);
        wireMyMockServerEndpoint2.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/three")));
    }
}
