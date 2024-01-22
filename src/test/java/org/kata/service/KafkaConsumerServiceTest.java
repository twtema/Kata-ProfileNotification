package org.kata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kata.dto.IndividualDto;
import org.kata.dto.individual.DocumentDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class KafkaConsumerServiceTest {

    public static long TIME_STAMP = 7 * 24 * 60 * 60 * 1000;

    public static ProducerFactory<String, Object> producerFactory;

    public static KafkaTemplate<String, Object> kafkaTemplate;

    public static ProducerRecord<String, Object> producerRecord;

    @InjectMocks
    KafkaConsumerService service;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    void producerConfig() throws IOException {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        producerFactory = new DefaultKafkaProducerFactory<>(props);

        kafkaTemplate = new KafkaTemplate<>(producerFactory);

        File individualFile = new File("src/test/resources/individual.json");
        IndividualDto individual = objectMapper.readValue(individualFile, IndividualDto.class);

        producerRecord = new ProducerRecord<>("createIndividual", 0, (System.currentTimeMillis() - TIME_STAMP), "message", (Object) individual);
    }

    @Test
    void listenTopic() {

        List<DocumentDto> documentList = service.getDocumentDto();

        kafkaTemplate.send(producerRecord);
        Assertions.assertEquals(documentList, service.getDocumentDto());

    }

}