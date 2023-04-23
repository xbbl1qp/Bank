package com.godel.test.bank.dao;

import java.math.BigInteger;
import java.util.Optional;

public interface BankDao<T> {

     Optional<T> findAccountById(BigInteger accountId);
     void save(T bankTransaction);

     Optional<T> showBalance(BigInteger accountId);
}
