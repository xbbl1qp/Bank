package com.godel.test.bank.dao.impl;

import com.godel.test.bank.dao.BankDao;
import com.godel.test.bank.dao.persistence.AccountRepository;
import com.godel.test.bank.dto.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class BankDaoImpl implements BankDao<Account> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<Account> findAccountById(BigInteger accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void save(Account bankTransaction) {
        accountRepository.save(bankTransaction);
    }

    @Override
    public Optional<Account> showBalance(BigInteger accountId) {
        return accountRepository.findById(accountId);
    }

}
