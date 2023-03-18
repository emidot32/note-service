package com.interview.noteservice.exception;

import lombok.Getter;

@Getter
public class NoEntityFound extends RuntimeException{
    public NoEntityFound(String message) {
        super(message);
    }
}
