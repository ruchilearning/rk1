package component.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.rk1.RkApplication;
import com.rk1.repository.HelloRepository;
import component.MyWireMockConfig;
import lombok.SneakyThrows;
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
@ActiveProfiles("test")
@TestPropertySource(properties = {"api-services.hello.baseUrl=http://localhost:8099"})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyComponentTest {

    @Autowired
    private HelloRepository helloRepository;

    @Autowired
    @Qualifier("wireMyMockServerEndpoint")
    private WireMockServer wireMyMockServerEndpoint;

    @Autowired
    @Qualifier("wireMyMockServerEndpoint2")
    private WireMockServer wireMyMockServerEndpoint2;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        this.wireMyMockServerEndpoint.start();
        this.wireMyMockServerEndpoint2.start();
    }

    @AfterEach
    public void tearDown() {
        this.wireMyMockServerEndpoint.stop();
        this.wireMyMockServerEndpoint2.stop();
    }

    @SneakyThrows
    @Test
    public void testMyEndpoint() {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);

        this.wireMyMockServerEndpoint.stubFor(WireMock.get(WireMock.urlEqualTo("/api/two"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));



        Mono<HelloRepository.HelloResponse> helloResponseMono = helloRepository.getExample();
        String expectedJson = objectMapper.writeValueAsString(helloResponseMono.block());


        Assertions.assertThat(expectedJson).isEqualTo(json);
        wireMyMockServerEndpoint.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/two")));
    }

    @SneakyThrows
    @Test
    public void testMyEndpoint2() {

        HelloRepository.HelloResponse expected = new HelloRepository.HelloResponse("Alice", 30, "alice@example.com");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(expected);

        this.wireMyMockServerEndpoint.stubFor(WireMock.get(WireMock.urlEqualTo("/api/two"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(json)));

        HelloRepository.HelloResponse helloResponse = helloRepository.getExample2();
        String expectedJson = objectMapper.writeValueAsString(helloResponse);

        Assertions.assertThat(expectedJson).isEqualTo(json);
    }
}
