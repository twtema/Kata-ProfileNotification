package org.kata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.kata.dto.individual.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaConsumerServiceTest {

    @Autowired
    KafkaConsumerService service;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void listenTopic() throws IOException {

        List<DocumentDto> documentList = service.getDocumentDto();

        kafkaTemplate().send(producerRecord());
        Assertions.assertEquals(documentList, service.getDocumentDto());

    }

    public ProducerFactory<String, Object> getProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(getProducer());
    }

    public ProducerRecord<String, Object> producerRecord() throws IOException {
        long timestamp = 7 * 24 * 60 * 60 * 1000;
        File individualFile = new File("src/test/resources/individual.json");
        Object individual = objectMapper.readValue(individualFile, Object.class);

        return new ProducerRecord<>("createIndividual", 0, (System.currentTimeMillis() - timestamp), "message", individual);
    }

}