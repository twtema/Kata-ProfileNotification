package org.kata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.dto.ContactChangeMessage;
import org.kata.dto.UpdateContactMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.create}")
    private String kafkaTopic;


    public void sendContactChangeNotification(UpdateContactMessage dto) {
        ContactChangeMessage message = ContactChangeMessage.builder()
                .icp(dto.getIcp())
                .oldContactValue(dto.getOldContactValue())
                .newContactValue(dto.getNewContactValue())
                .confirmationCode(generateConfirmationCode())
                .build();

        kafkaTemplate.send(kafkaTopic, message);

        log.info("В топик: {} для пользователя с icp: {} отправлен код подтверждения", kafkaTopic, dto.getIcp());
    }

    private String generateConfirmationCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
}
