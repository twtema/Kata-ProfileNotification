package org.kata.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static org.kata.dto.enums.ContactMediumType.PHONE;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageSender {

    private final NotificationSender notificationSender;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic1.create}")
    private String kafkaTopic;

    @Value("${kafka.topic2.create}")
    private String kafkaDocumentsTopic;

    @Value("${kafka.topic3.create}")
    private String kafkaNewIndividualTopic;

    public void sendContactChangeNotification(UpdateContactMessage dto) {
        ContactChangeMessage message = ContactChangeMessage.builder()
                .icp(dto.getIcp())
                .oldContactValue(dto.getOldContactValue())
                .newContactValue(dto.getNewContactValue())
                .confirmationCode(generateConfirmationCode())
                .build();

        kafkaTemplate.send(kafkaTopic, message);

        notificationSender.sendNotification(message);

        log.info("В топик: {} для пользователя с icp: {} отправлен код подтверждения", kafkaTopic, dto.getIcp());

    }

    private String generateConfirmationCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }

    public void forseUpdate(List<DocumentDto> documentDto) {
        kafkaTemplate.send(kafkaDocumentsTopic, documentDto);

        log.info("Document send to topic: {}", kafkaDocumentsTopic);
    }

    public void sendToNewIndividual(IndividualDto dto) {
        NewIndividualMessage message = NewIndividualMessage.builder()
                .icp(dto.getIcp())
                .name(dto.getName())
                .surname(dto.getSurname())
                .patronymic(dto.getPatronymic())
                .phoneNumber(dto.getContacts()
                        .stream()
                        .filter(x -> x.getType().equals(PHONE))
                        .map(ContactMediumDto::getValue)
                        .collect(Collectors.toList()))
                .build();

        kafkaTemplate.send(kafkaNewIndividualTopic, message);

        log.info("В топик: {} для пользователя с icp: {} отправлены name, surname, patronymic, list of phone numbers",
                kafkaNewIndividualTopic, dto.getIcp());
    }
}
