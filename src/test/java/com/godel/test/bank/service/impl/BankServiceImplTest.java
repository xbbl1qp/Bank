package com.godel.test.bank.service.impl;

import com.godel.test.bank.common.constants.Currency;
import com.godel.test.bank.common.exception.AccountNotFoundException;
import com.godel.test.bank.common.exception.InsufficientBalanceException;
import com.godel.test.bank.common.service.CurrencyService;
import com.godel.test.bank.dao.BankDao;
import com.godel.test.bank.dto.entity.Account;
import com.godel.test.bank.dto.request.BankTransaction;
import com.godel.test.bank.service.BankService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.verification.VerificationMode;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Optional;

public class BankServiceImplTest {

    @Mock
    BankDao bankDao;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    BankService bankService;

    @BeforeEach
    void initialize() {
        bankService = new BankServiceImpl();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDebitPositive() {
        Optional<Account> account= getCorrectAccount();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.doNothing().when(bankDao).save(Mockito.any());
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        bankService.debit(getBankTransaction());
        Assert.isTrue(account.get().getBalance()==110d,"Correct Balance");
    }

    @Test
    public void testCreditPositive() {
        Optional<Account> account= getCorrectAccount();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.doNothing().when(bankDao).save(Mockito.any());
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        bankService.credit(getBankTransaction());
        Assert.isTrue(account.get().getBalance()==330d,"Correct Balance");
    }

    @Test
    void testCreditDifferentCurrency() {
        Optional<Account> account= getInCorrectAccount();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.doNothing().when(bankDao).save(Mockito.any());
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        bankService.credit(getBankTransaction());
        Assert.isTrue(!(account.get().getBalance()==330d),"InCorrect Balance");
    }

    @Test
    public void testDebitDifferentCurrency() {
        Optional<Account> account= getInCorrectAccount();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.doNothing().when(bankDao).save(Mockito.any());
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        bankService.debit(getBankTransaction());
        Assert.isTrue(!(account.get().getBalance()==330d),"InCorrect Balance");
    }

    @Test
    public void testCreditNoAccountException() {
        Optional<Account> account= Optional.empty();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        AccountNotFoundException ex =Assertions.assertThrows(AccountNotFoundException.class,()->bankService.credit(getBankTransaction()));
        Assert.isTrue(ex.getMessage().contains("Account id: 1 does not exist"),"Exception Message Checked");
     }

    @Test
    public void testDebitNoAccountException() {
        Optional<Account> account= Optional.empty();
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(account);
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        AccountNotFoundException ex =Assertions.assertThrows(AccountNotFoundException.class,()->bankService.debit(getBankTransaction()));
        Assert.isTrue(ex.getMessage().contains("Account id: 1 does not exist"),"Exception Message Checked");
    }

    @Test
    public void testDebitInsufficientBalanceException() {
        Account account= getCorrectAccount().get();
        account.setBalance(1d);
        Mockito.when(bankDao.findAccountById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(currencyService.applyExchange(Mockito.any(),Mockito.any(),Mockito.any())).thenCallRealMethod();
        InsufficientBalanceException ex =Assertions.assertThrows(InsufficientBalanceException.class, ()->bankService.debit(getBankTransaction()));
        Assert.isTrue(ex.getMessage().contains("Balance is not sufficient for this transaction in accountId : 1"),"Exception Message Checked");
    }


    private BankTransaction getBankTransaction(){
        return BankTransaction.builder()
                        .accountId(BigInteger.valueOf(1))
                        .amount(110d)
                        .currency(Currency.INR)
                              .build();
    }

    private Optional<Account> getCorrectAccount(){
        return Optional.of(Account.builder()
                              .accountId(BigInteger.valueOf(1))
                              .balance(220d)
                              .currency(Currency.INR.name())
                              .build());
    }

    private Optional<Account> getInCorrectAccount(){
        return Optional.of(Account.builder()
                                  .accountId(BigInteger.valueOf(1))
                                  .balance(220d)
                                  .currency(Currency.USD.name())
                                  .build());
    }



}
