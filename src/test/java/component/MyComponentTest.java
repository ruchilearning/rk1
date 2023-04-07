package component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.rk1.RkApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = RkApplication.class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@AutoConfigureWireMock(port = 8081)
public class MyComponentTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @BeforeEach
    public void setup() {
        WireMock.configureFor("localhost", wireMockServer.port());
        wireMockServer.start();
    }

    @Test
    public void testMyEndpoint() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/hello"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\n" +
                                "    \"name\": \"Alice\",\n" +
                                "    \"age\": 30,\n" +
                                "    \"email\": \"alice@example.com\"\n" +
                                "  }")));

        webTestClient.get()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk();
//                .expectBody(MyResponse.class)
//                .isEqualTo(new MyResponse("Mocked response"));

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/hello")));
    }

    @Test
    public void testMyService() {
        String expectedResponse = "{\n" +
                "    \"name\": \"Alice\",\n" +
                "    \"age\": 30,\n" +
                "    \"email\": \"alice@example.com\"\n" +
                "  }";

        WireMock.stubFor(WireMock.get("/hello")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(expectedResponse)));

        String actualResponse = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/hello")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Assertions.assertThat(actualResponse).isEqualTo(expectedResponse);
    }

}
