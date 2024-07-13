package com.gamemetricbackend.global.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "User Not Match")
public class UserNotMatchException extends Exception {
    public UserNotMatchException() {
        super("the user does not match the profile of the owner of the broadcast");
    }
    public UserNotMatchException(String message) {
        super(message);
    }
}
