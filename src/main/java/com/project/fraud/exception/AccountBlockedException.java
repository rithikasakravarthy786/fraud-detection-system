package com.project.fraud.exception;

public class AccountBlockedException extends RuntimeException {
    public AccountBlockedException(String msg) {
        super(msg);
    }
}

