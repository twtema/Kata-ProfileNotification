package org.kata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.kata.dto.IndividualDto;
import org.kata.dto.NewIndividualMessage;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaMessageSenderTest {

    private static final String KAFKA_NEW_INDIVIDUAL_TOPIC = "new-individual";

    private NewIndividualMessage testNewIndividualMessage;

    private IndividualDto testIndividualDto;

    private ListenableFuture<SendResult<String, Object>> mockListenableFuture;

    private SendResult<String, Object> mockSendResult;

    private ListenableFutureCallback<SendResult<String, Object>> mockListenableFutureCallback;

    @Mock
    private KafkaTemplate<String, Object> mockKafkaTemplate;

    @InjectMocks
    private KafkaMessageSender kafkaMessageSender;

    @BeforeEach
    void setUp() throws IOException {
        ReflectionTestUtils.setField(kafkaMessageSender, "kafkaNewIndividualTopic", "new-individual");

        mockListenableFuture = mock(ListenableFuture.class);
        mockSendResult = mock(SendResult.class);
        mockListenableFutureCallback = mock(ListenableFutureCallback.class);

        ObjectMapper objectMapper = new ObjectMapper();
        File individualFile = new File("src/test/resources/individual.json");
        testIndividualDto = objectMapper.readValue(individualFile, IndividualDto.class);

        testNewIndividualMessage = NewIndividualMessage.builder()
                .icp("1234552380")
                .name("Дейл")
                .surname("Стивенс")
                .patronymic("Иванович")
                .phoneNumber(Collections.emptyList())
                .build();
    }

    @Test
    void sendToNewIndividualTest_onSuccess_isCalled() {
        when(mockKafkaTemplate.send(KAFKA_NEW_INDIVIDUAL_TOPIC, testNewIndividualMessage)).thenReturn(mockListenableFuture);

        doAnswer(invocationOnMock -> {
            ListenableFutureCallback<SendResult<String, Object>> callback = invocationOnMock.getArgument(0);
            callback.onSuccess(mockSendResult);
            return null;
        }).when(mockListenableFuture).addCallback(any(ListenableFutureCallback.class));
        mockListenableFuture.addCallback(mockListenableFutureCallback);
        kafkaMessageSender.sendToNewIndividual(testIndividualDto);

        Mockito.verify(mockListenableFutureCallback).onSuccess(mockSendResult);
        Mockito.verify(mockKafkaTemplate, Mockito.times(1)).send(KAFKA_NEW_INDIVIDUAL_TOPIC, testNewIndividualMessage);
        Assertions.assertThat(testIndividualDto).isNotNull();
        Assertions.assertThat(testNewIndividualMessage).isNotNull();
    }

    @Test
    void sendToNewIndividualTest_onFailure_isCalled() {
        RuntimeException ex = new RuntimeException("error");
        when(mockKafkaTemplate.send(KAFKA_NEW_INDIVIDUAL_TOPIC, testNewIndividualMessage)).thenReturn(mockListenableFuture);

        doAnswer(invocationOnMock -> {
            ListenableFutureCallback<SendResult<String, Object>> callback = invocationOnMock.getArgument(0);
            callback.onFailure(ex);
            return null;
        }).when(mockListenableFuture).addCallback(any(ListenableFutureCallback.class));
        mockListenableFuture.addCallback(mockListenableFutureCallback);
        kafkaMessageSender.sendToNewIndividual(testIndividualDto);

        Mockito.verify(mockListenableFutureCallback).onFailure(ex);
        Mockito.verify(mockKafkaTemplate, Mockito.times(1)).send(KAFKA_NEW_INDIVIDUAL_TOPIC, testNewIndividualMessage);
    }
}