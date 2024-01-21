//package org.kata.service;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
//
//public class KafkaMessageSenderTest {
//
//    private String kafkaNewIndividualTopic;
//    @Mock
//    KafkaTemplate<String, Object> kafkaTemplate;
//
//    @InjectMocks
//    KafkaMessageSender kafkaMessageSender;
//
//    @Before
//    public void setUp() {
//        ReflectionTestUtils.setField(kafkaMessageSender, "kafkaNewIndividualTopic", "new-individual");
//        kafkaNewIndividualTopic = "new-individual";
//    }
//
//
//    @Test
//    public void sendToNewIndividualTest() {
//
//    }
//
//}