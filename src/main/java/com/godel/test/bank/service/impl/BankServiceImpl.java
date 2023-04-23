package com.godel.test.bank.service.impl;

import com.godel.test.bank.common.exception.AccountNotFoundException;
import com.godel.test.bank.common.exception.InsufficientBalanceException;
import com.godel.test.bank.common.service.CurrencyService;
import com.godel.test.bank.dao.BankDao;
import com.godel.test.bank.dto.entity.Account;
import com.godel.test.bank.dto.request.BankTransaction;
import com.godel.test.bank.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    @Autowired
    private BankDao bankDaoImpl;

    @Autowired
    private CurrencyService service;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void credit(BankTransaction bankTransaction) {

        Optional<Account> account = bankDaoImpl.findAccountById(bankTransaction.getAccountId());
        if(account.isPresent()){
            Account accountDetails = account.get();
            Double creditAmount = service.applyExchange(accountDetails.getCurrency(),bankTransaction.getCurrency(),bankTransaction.getAmount());
            accountDetails.setBalance(accountDetails.getBalance()+creditAmount);
            bankDaoImpl.save(accountDetails);
        } else {
            LOGGER.error("Account id: {} does not exist", bankTransaction.getAccountId());
            throw new AccountNotFoundException("Account id: " + bankTransaction.getAccountId() + " does not exist");
        }

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void debit(BankTransaction bankTransaction) {

        Optional<Account> account = bankDaoImpl.findAccountById(bankTransaction.getAccountId());
        if(account.isPresent()){
            Account accountDetails = account.get();
            Double debitAmount = service.applyExchange(accountDetails.getCurrency(),bankTransaction.getCurrency(),bankTransaction.getAmount());
            if(accountDetails.getBalance()>debitAmount)
                accountDetails.setBalance(accountDetails.getBalance()-debitAmount);
            else {
                LOGGER.error("Balance is not sufficient for this transaction in accountId : {}", bankTransaction.getAccountId());
                throw new InsufficientBalanceException("Balance is not sufficient for this transaction in accountId : " +
                                                       bankTransaction.getAccountId());
            }
            bankDaoImpl.save(accountDetails);
        } else {
            LOGGER.error("Account id: {} does not exist", bankTransaction.getAccountId());
            throw new AccountNotFoundException("Account id: "+bankTransaction.getAccountId()+" does not exist");
        }
    }

    @Override
    public Double showBalance(BigInteger accountId) {
        Optional<Account> account=  bankDaoImpl.showBalance(accountId);
        if(account.isPresent()) return account.get().getBalance();
        throw new AccountNotFoundException("Account id: "+accountId+" does not exist");
    }
}
