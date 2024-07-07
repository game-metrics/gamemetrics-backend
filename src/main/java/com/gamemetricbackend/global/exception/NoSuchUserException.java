package com.gamemetricbackend.global.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "No Such User")
public class NoSuchUserException extends Exception{

    public NoSuchUserException() {
        super("해당유저를 찾을수 없습니다.");
    }
}
