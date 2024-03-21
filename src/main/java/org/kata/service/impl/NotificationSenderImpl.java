package org.kata.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kata.bot.NotificationBot;
import org.kata.dto.ContactChangeMessage;
import org.kata.dto.enums.NotificationType;
import org.kata.service.NotificationSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSenderImpl implements NotificationSender {

    @Value("${bot.chat-id}")
    private Long telegramChatId;
    private final NotificationBot notificationBot;

    @Override
    public void sendNotification(ContactChangeMessage message) {
        var text = "Type: " + NotificationType.VERIFICATION_CODE + "\n" +
                "Conversation ID: " + message.getConversationId() + "\n" +
                "Your ICP: " + message.getIcp() + "\n" +
                "Verification code: " + message.getConfirmationCode() + "\n" +
                "This code is valid for 15 minutes and expires at " + getExpirationTime();
        notificationBot.sendText(telegramChatId, text);

        log.info("Conversation ID: {} Отправлено в телеграм: {}", message.getConversationId(), text);
    }

    private String getExpirationTime() {
        Date currentTime = new Date();
        int expirationTime = 15 * 60 * 1000;
        currentTime.setTime(System.currentTimeMillis() + expirationTime);
        return currentTime.toString();
    }

}
