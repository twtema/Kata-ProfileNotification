package org.kata.dto.individual;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kata.dto.enums.CurrencyType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class WalletDto {

    private String icp;

    private String walletId;

    private CurrencyType currencyType;

    private BigDecimal balance;

}
