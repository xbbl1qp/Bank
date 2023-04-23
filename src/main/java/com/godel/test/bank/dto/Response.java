package com.godel.test.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private T data;
    private String message;
    private LocalDateTime timeStamp;
    private String path;
    private HttpStatus status;

}
