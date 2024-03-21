package org.kata.dto.individual;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kata.dto.enums.ContactMediumType;

@Data
@NoArgsConstructor
public class ContactMediumDto {

    private String conversationId;

    private String icp;

    private ContactMediumType type;

    private String value;
}