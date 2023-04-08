package component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

@Configuration
public class MyWireMockConfig {

    @Bean(name = "wireMyMockServerEndpoint")
    public WireMockServer wireMyMockServerEndpoint() {
        WireMockConfiguration configuration = WireMockConfiguration.options().port(8099);
        return new WireMockServer(configuration);
    }

    @Bean(name = "wireMyMockServerEndpoint2")
    public WireMockServer wireMyMockServerEndpoint2() {
        WireMockConfiguration configuration = WireMockConfiguration.options().port(8091);
        return new WireMockServer(configuration);
    }

}