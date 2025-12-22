package com.starter.clinic.exception;

public class UnrecognizedException extends RuntimeException {
    public UnrecognizedException(String message) {
        super(message);
    }
}
