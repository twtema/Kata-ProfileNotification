package org.kata.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kata.dto.enums.GenderType;
import org.kata.dto.individual.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IndividualDto {

    private String conversationId;

    private String icp;

    private String name;

    private String surname;

    private String patronymic;

    private String fullName;

    private GenderType gender;

    private String placeOfBirth;

    private String countryOfBirth;

    private Date birthDate;

    private List<DocumentDto> documents;

    private List<ContactMediumDto> contacts;

    private List<AddressDto> address;

    private List<AvatarDto> avatar;

    private List<WalletDto> wallet;
}

