package com.godel.test.bank.common.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(
                    AccountNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("message", "Account Id not found");
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceException(
                    InsufficientBalanceException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("message", "Insufficient Balance for Account");
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timeStamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("errors", ex.getMessage());
        body.put("path",request.getContextPath());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
