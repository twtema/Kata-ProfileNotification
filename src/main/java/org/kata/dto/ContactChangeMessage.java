package org.kata.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ContactChangeMessage {

    private String icp;
    private String oldContactValue;
    private String newContactValue;
    private String confirmationCode;
}
