package component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MyWireMockConfig {

    @Bean(name = "myWiremockServer", initMethod = "start", destroyMethod = "stop")
    @Primary
    public WireMockServer wireMockServer() {
        WireMockConfiguration config = WireMockConfiguration
                .wireMockConfig()
                .port(8089);
        return new WireMockServer(config);
    }

}