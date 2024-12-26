package com.gamemetricbackend.domain.chat.websockettest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
        //처음 입장인지 아닌지 구별하는 Enum
    }
    //단순 DTO
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
