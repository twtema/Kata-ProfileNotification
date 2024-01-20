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

    public DocumentDto(String number, DocumentType documentType, String number1, String number2, Date date, Date date1, boolean b) {
    }
}