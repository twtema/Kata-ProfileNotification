package org.kata.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.dto.IndividualDto;
import org.kata.dto.UpdateContactMessage;
import org.kata.dto.individual.DocumentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
@Slf4j
public class KafkaConsumerService {

    private final KafkaMessageSender kafkaMessageSender;

    @Value("${kafka.topic1.listen}")
    private String kafkaTopicListen1;

    @Value("${kafka.topic2.listen}")
    private String kafkaTopicListen2;

    private final String KAFKA_TOPIC_1_LISTEN = "${kafka.topic1.listen}";

    private final String KAFKA_TOPIC_2_LISTEN = "${kafka.topic2.listen}";

    private final ObjectMapper objectMapper;

    private List<DocumentDto> documentDto;

    @KafkaListener(topics = KAFKA_TOPIC_1_LISTEN,
            groupId = "${spring.kafka.consumer1.group-id}",
            containerFactory = "filterKafkaListenerContainerFactory")
    public void listenContactChange(String message) throws JsonProcessingException {

        UpdateContactMessage dto = objectMapper.readValue(message, UpdateContactMessage.class);

        kafkaMessageSender.sendContactChangeNotification(dto);

        log.info("Conversation ID: {} Отправлено в топик: {} для пользователя с icp: {}", dto.getConversationId(), kafkaTopicListen1, dto.getIcp());

    }

    @KafkaListener(topics = KAFKA_TOPIC_2_LISTEN,
            topicPartitions = {
            @TopicPartition(topic = KAFKA_TOPIC_2_LISTEN,
                    partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "83"))},
            groupId = "${spring.kafka.consumer2.group-id}",
            containerFactory = "filterKafkaListenerContainerFactoryObject")
    public void listenIndividual(IndividualDto message) {
        documentDto = message.getDocuments();
        kafkaMessageSender.forseUpdate(documentDto);

        log.info("Conversation ID: {} Отправлено в топик: {} для пользователя с icp: {}", message.getConversationId(), kafkaTopicListen2, message.getIcp());
    }

    @KafkaListener(topics = KAFKA_TOPIC_2_LISTEN, groupId = "${spring.kafka.consumer1.group-id}")
    public void sendToNewIndividual(String message) throws JsonProcessingException {

        IndividualDto dto = objectMapper.readValue(message, IndividualDto.class);

        kafkaMessageSender.sendToNewIndividual(dto);

        log.info("Conversation ID: {} Отправлено в топик: {} для пользователя с icp: {}", dto.getConversationId(), kafkaTopicListen2, dto.getIcp());
    }
}
