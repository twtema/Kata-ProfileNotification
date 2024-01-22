package org.kata.dto;

import lombok.Builder;
import lombok.Data;
import org.kata.dto.enums.DocumentType;

import java.util.Date;

@Data
@Builder
public class DocumentMessage {
    private String icp;

    private DocumentType documentType;

    private String documentNumber;

    private String documentSerial;

    private Date issueDate;

    private Date expirationDate;

    private boolean actual;

}
