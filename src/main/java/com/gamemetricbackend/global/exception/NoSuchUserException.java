package com.gamemetricbackend.global.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "No Such User")
public class NoSuchUserException extends Exception{

    public NoSuchUserException() {
        super("User is not found");
    }
    public NoSuchUserException(String message) {
        super(message);
    }
}
