package com.gamemetricbackend.domain.stream.websocket;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class StreamDto {
    public enum StreamStatusType{
        STREAM,JOIN, LEAVE
    }
        private Long streamId;
        private String streamer;
        private String message;

}
