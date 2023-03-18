package com.interview.noteservice.exception;

import lombok.Getter;

@Getter
public class NoEntityFoundException extends RuntimeException{
    public NoEntityFoundException(String message) {
        super(message);
    }
}
