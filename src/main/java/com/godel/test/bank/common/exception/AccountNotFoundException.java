package com.godel.test.bank.common.exception;

public class AccountNotFoundException extends IllegalArgumentException{
        public AccountNotFoundException(String message){
            super(message);
        }


}
