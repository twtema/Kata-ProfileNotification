package org.kata.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class NewIndividualMessage {

    private String icp;

    private String name;

    private String surname;

    private String patronymic;

    private List<String> phoneNumber;
}
