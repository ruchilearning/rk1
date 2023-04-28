package com.rk1.service;

import com.rk1.kafka.KafkaProducer;
import com.rk1.repository.entity.Details;
import com.rk1.repository.entity.DetailsRepository;
import com.rk5.avro01.Avro01;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyListenerService {
    private final DetailsRepository detailsRepository;
    private final KafkaProducer kafkaProducer;

    public void processKafkaMessage(Avro01 avro01) {
        detailsRepository.save(Details.builder().uuid(avro01.getUuid()).details(avro01.getFirstName() +" "
                + avro01.getLastName()).active(avro01.getActive()).build());
        kafkaProducer.sendMessage("test2", avro01.getUuid() + ":" + avro01.getFirstName() +" "
                + avro01.getLastName());
    }
}