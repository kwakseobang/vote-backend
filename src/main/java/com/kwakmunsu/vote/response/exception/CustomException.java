package com.kwakmunsu.vote.response.exception;

import com.kwakmunsu.vote.response.ResponseCode;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    private ResponseCode errorResponseCode;
    private String message;

    public CustomException(ResponseCode errorResponseCode, String message) {
        this.errorResponseCode = errorResponseCode;
        this.message = message;
    }


}