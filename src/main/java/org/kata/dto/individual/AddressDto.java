package org.kata.dto.individual;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto {

    private String icp;

    private String street;

    private String city;

    private String state;

    private String postCode;

    private String country;
}