package org.kata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kata.dto.UpdateContactMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final KafkaMessageSender kafkaMessageSender;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic.listen}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenContactChange(String message) throws JsonProcessingException {

        UpdateContactMessage dto = objectMapper.readValue(message, UpdateContactMessage.class);

        kafkaMessageSender.sendContactChangeNotification(dto);

    }

}
