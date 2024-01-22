package org.kata.dto.individual;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.kata.dto.enums.DocumentType;

import java.util.Date;

@Data
@NoArgsConstructor
public class DocumentDto {

    private String icp;

    private DocumentType documentType;

    private String documentNumber;

    private String documentSerial;

    private Date issueDate;

    private Date expirationDate;

    private boolean actual;
}