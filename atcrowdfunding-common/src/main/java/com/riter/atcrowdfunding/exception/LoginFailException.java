package com.riter.atcrowdfunding.exception;

public class LoginFailException extends RuntimeException {

    public LoginFailException(String message){
        super(message);
    }
}
