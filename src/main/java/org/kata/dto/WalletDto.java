package org.kata.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.kata.dto.enums.CurrencyType;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class WalletDto {

    private String icp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String walletId;

    private CurrencyType currencyType;

    private BigDecimal balance;

}
