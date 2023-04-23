package com.godel.test.bank.common.exception;

public class InsufficientBalanceException extends IllegalArgumentException{
        public InsufficientBalanceException(String message){
            super(message);
        }


}
