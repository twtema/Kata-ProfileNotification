package org.kata.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kata.dto.individual.DocumentDto;
import org.kata.dto.IndividualDto;
import org.kata.dto.UpdateContactMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final KafkaMessageSender kafkaMessageSender;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${kafka.topic1.listen}",
            groupId = "${spring.kafka.consumer1.group-id}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenContactChange(String message) throws JsonProcessingException {

        UpdateContactMessage dto = objectMapper.readValue(message, UpdateContactMessage.class);

        kafkaMessageSender.sendContactChangeNotification(dto);

    }

    @KafkaListener(topics = "${kafka.topic2.listen}",
            topicPartitions = {
            @TopicPartition(topic = "${kafka.topic2.listen}",
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "83"))},
            groupId = "${spring.kafka.consumer2.group-id}",
            containerFactory = "filterKafkaListenerContainerFactoryObject")
    public void listenIndividual(IndividualDto message) throws JsonProcessingException {
        List<DocumentDto> documentDto = message.getDocuments();
        kafkaMessageSender.forseUpdate(documentDto);
        System.out.println(documentDto.toString());
    }

}
