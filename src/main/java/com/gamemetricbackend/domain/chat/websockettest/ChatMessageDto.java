package com.gamemetricbackend.domain.chat.websockettest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    public enum MessageType{
        JOIN, TALK, LEAVE
    }
    //단순 DTO
    private MessageType type;
    private Long roomId;
    private String sender;
    private String message;
}
