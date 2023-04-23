package com.godel.test.bank.service;

import com.godel.test.bank.dto.request.BankTransaction;

import java.math.BigInteger;

public interface BankService {

     void credit(BankTransaction bankTransaction);

     void debit(BankTransaction bankTransaction);

     Double showBalance(BigInteger accountId);
}
