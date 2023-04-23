package com.godel.test.bank.common.service;

import com.godel.test.bank.common.constants.Currency;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CurrencyService {

    public Double applyExchange(String input, Currency target,Double amount){
        //TODO call 3rd party currency conversion API
        if(input.equals(target.name())) return amount;
        return 0.1*amount;
    }
}
