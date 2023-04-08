package configs;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.rk1.configs"})
public class MyWireMockConfig {

    @Bean(name = "wireMyMockServerEndpoint")
    public WireMockServer wireMyMockServerEndpoint() {
        WireMockConfiguration configuration = WireMockConfiguration.options().port(8099);
        return new WireMockServer(configuration);
    }

    @Bean(name = "wireMyMockServerEndpoint2")
    public WireMockServer wireMyMockServerEndpoint2() {

        //  worked  .usingFilesUnderDirectory("/Users/ruchi/Desktop/dev/java/rk1/src/test/resources/");
        //  worked  .usingFilesUnderDirectory("/Users/ruchi/Desktop/dev/java/rk1/src/test/resources/rk/");
        //  Path resourceDirectory = Paths.get("src","test","resources", "rk");
        //  String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        //  worked  .usingFilesUnderDirectory(absolutePath)
        //  worked  .usingFilesUnderClasspath("src/test/resources/rk");

        WireMockConfiguration configuration = WireMockConfiguration.options()
                .port(8091)
                .usingFilesUnderClasspath("src/test/resources/rk");

        return new WireMockServer(configuration);
    }

}