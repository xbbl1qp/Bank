package com.godel.test.bank.dto.request;

import com.godel.test.bank.common.constants.Currency;

import java.math.BigInteger;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class BankTransaction {

    @NonNull
    private BigInteger accountId;
    @NonNull
    private Double amount;
    @NonNull
    private Currency currency;
}
