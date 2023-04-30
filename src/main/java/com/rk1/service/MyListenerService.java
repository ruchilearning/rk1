package com.rk1.service;

import com.rk1.kafka.KafkaProducer;
import com.rk1.repository.entity.Details;
import com.rk1.repository.entity.DetailsRepository;
import com.rk5.avro01.Avro01;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyListenerService {
    private final DetailsRepository detailsRepository;
    private final KafkaProducer kafkaProducer;

    public void processKafkaMessage(Avro01 avro01) {

        if (avro01.getActive() == false) {
            throw new RuntimeException();
        }

        try {
            detailsRepository.save(Details.builder().uuid(avro01.getUuid()).details(avro01.getFirstName() +" "
                    + avro01.getLastName()).active(avro01.getActive()).build());;
        } catch (JDBCConnectionException | DataAccessException | SQLGrammarException |
                 CannotCreateTransactionException e) {
            log.error("Error occurred while saving Details 2: {}", e.getCause());
            throw e;

        } catch (Exception e) {
            log.error("Error occurred while saving Details: {}", e.getCause());

        }

        kafkaProducer.sendMessage("test2", avro01.getUuid() + ":" + avro01.getFirstName() +" "
                + avro01.getLastName());
    }
}