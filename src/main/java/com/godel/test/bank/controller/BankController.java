package com.godel.test.bank.controller;

import com.godel.test.bank.common.constants.APIConstants;
import com.godel.test.bank.dao.impl.BankDaoImpl;
import com.godel.test.bank.dto.Response;
import com.godel.test.bank.dto.request.BankTransaction;
import com.godel.test.bank.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(APIConstants.ACCOUNT+APIConstants.V1)
@Slf4j
public class BankController {

    @Autowired
    private BankService bankServiceImpl;
    @Autowired
    private BankDaoImpl bankDaoImpl;

    @PostMapping(APIConstants.CREDIT)
    public Response<Object> credit(@RequestBody BankTransaction bankTransaction){
        LOGGER.info("credit for account id : {}",bankTransaction.getAccountId());
       bankServiceImpl.credit(bankTransaction);
        return Response.builder()
                       .status(HttpStatus.OK)
                       .path(APIConstants.CREDIT)
                       .timeStamp(LocalDateTime.now())
                       .message(APIConstants.SUCCESS)
                       .build();
    }

    @PostMapping(APIConstants.DEBIT)
    public Response<Object> debit(@RequestBody BankTransaction bankTransaction){
        LOGGER.info("debit for account id : {}",bankTransaction.getAccountId());
        bankServiceImpl.debit(bankTransaction);
        return Response.builder()
                       .status(HttpStatus.OK)
                       .path(APIConstants.DEBIT)
                       .timeStamp(LocalDateTime.now())
                       .message(APIConstants.SUCCESS)
                       .build();
    }

    @GetMapping(APIConstants.BALANCE)
    public Response<Object> showBalance(@RequestParam BigInteger accountId){
        LOGGER.info("Show Balance for account id : {}",accountId);
        return Response.builder()
                        .status(HttpStatus.OK)
                        .data(bankServiceImpl.showBalance(accountId))
                        .path(APIConstants.BALANCE)
                        .timeStamp(LocalDateTime.now())
                        .message(APIConstants.SUCCESS)
                       .build();

    }



}
