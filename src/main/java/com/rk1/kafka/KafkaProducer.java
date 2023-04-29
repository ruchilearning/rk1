package com.rk1.kafka;

import com.rk1.configs.KafkaConfigProperties;
import com.rk5.avro01.Avro01;
import com.rk5.user.UserCreated;
import com.rk5.user.UserUpdated;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.confluent.kafka.serializers.subject.TopicRecordNameStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, Avro01> kafkaTemplateAvro;
    private KafkaTemplate<String, Object> kafkaTemplateTopicRecord;
    @Autowired
    private  KafkaConfigProperties kafkaConfigProperties;

    @PostConstruct
    public void KafkaProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaConfigProperties.getProducer().getKeySerializer());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaConfigProperties.getProducer().getValueSerializer());
        DefaultKafkaProducerFactory<String, String> pf = new DefaultKafkaProducerFactory<>(props);
        KafkaTemplate<String, String> template = new KafkaTemplate<>(pf);
        this.kafkaTemplate = template;

    }

    @PostConstruct
    private void KafkaProducerAvro() {
        Map<String, Object> propsAvro = new HashMap<>();
        propsAvro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
        propsAvro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
        propsAvro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        propsAvro.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        propsAvro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        propsAvro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        propsAvro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        propsAvro.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        DefaultKafkaProducerFactory<String, Avro01> pf2 = new DefaultKafkaProducerFactory<>(propsAvro);
        KafkaTemplate<String, Avro01> template2 = new KafkaTemplate<>(pf2);
        this.kafkaTemplateAvro = template2;
    }


    @PostConstruct
    private void KafkaProducerAvroTopicRecord() {
        Map<String, Object> propsTopicRecord = new HashMap<>();
        propsTopicRecord.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigProperties.getBootstrapServers());
        propsTopicRecord.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,  StringSerializer.class);
        propsTopicRecord.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        propsTopicRecord.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        propsTopicRecord.put(KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY, TopicRecordNameStrategy.class);
        DefaultKafkaProducerFactory<String, Object> pf3 = new DefaultKafkaProducerFactory<>(propsTopicRecord);
        KafkaTemplate<String, Object> template3 = new KafkaTemplate<>(pf3);
        this.kafkaTemplateTopicRecord = template3;
    }

    public void sendMessage(String topic, String message) {
        log.info("rk1 send sendMessage called topic:" + topic);
        kafkaTemplate.send(topic, message);
    }

    public void sendMessageAvro(String topic, Avro01 message) {
        log.info("rk1 send sendMessageAvro called topic:" + topic);
        kafkaTemplateAvro.send(topic, UUID.randomUUID().toString(), message);
    }

    public void sendMessageAvroTopicRecord(String topic, UserUpdated message) {
        log.info("rk1 send sendMessageAvroTopicRecord called topic:" + topic);
        kafkaTemplateTopicRecord.send(topic, UUID.randomUUID().toString(), message);
    }

    public void sendMessageAvroTopicRecord(String topic, UserCreated message) {
        log.info("rk1 send sendMessageAvroTopicRecord called topic:" + topic);
        kafkaTemplateTopicRecord.send(topic, UUID.randomUUID().toString(), message);
    }
}
