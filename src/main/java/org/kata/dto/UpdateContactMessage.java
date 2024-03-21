package org.kata.dto;

import lombok.Data;

@Data
public class UpdateContactMessage {

    private String conversationId;
    private String icp;
    private String oldContactValue;
    private String newContactValue;
}
