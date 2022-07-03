package com.example.instagram.exception;

import lombok.Getter;

@Getter
public class CommonErrorException extends RuntimeException{
    private ErrorCode errorCode;

    public CommonErrorException( ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
