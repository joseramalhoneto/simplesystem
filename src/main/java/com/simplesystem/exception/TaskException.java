package com.simplesystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class TaskException extends RuntimeException{
    public TaskException(String message) {
        super(message);
    }
}