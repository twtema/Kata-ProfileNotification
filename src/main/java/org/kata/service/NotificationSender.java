package org.kata.service;

import org.kata.dto.ContactChangeMessage;

public interface NotificationSender {

    void sendNotification(ContactChangeMessage message);

}
