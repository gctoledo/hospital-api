package com.starter.schedule.exception;

public class UnavailableScheduleException extends RuntimeException {
    public UnavailableScheduleException(String message) {
        super(message);
    }
}
