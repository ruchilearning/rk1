package com.rk1.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "external.kafka")
@Data
public class KafkaConfigProperties {

    private String bootstrapServers;
    private String clientId;
    private Producer producer;
    private SchemaRegistry schemaRegistry;
    private Consumer consumer;

    @Data
    public static class Producer {
        private Map<String, Object> properties;
        private String keySerializer;
        private String valueSerializer;
    }

    @Data
    public static class SchemaRegistry {
        private String url;
        private String subjectNameStrategy;
        private boolean useLatestVersion;
    }

    @Data
    public static class Consumer {
        private String groupId;
        private String autoOffsetReset;
        private boolean enableAutoCommit;
        private String keyDeserializer;
        private String valueDeserializer;
        private Map<String, Object> properties;
    }

    @Data
    public static class ExtendedSchemaRegistry extends SchemaRegistry {
        private String subjectNameStrategy;
        private boolean useLatestVersion;
    }
}
