package com.gamemetricbackend.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebRTCDto {
    private String type; // "offer", "answer", "candidate"
    private String senderSessionId;
    private String targetSessionId;
    private Object data; // SDP 또는 ICE Candidate 정보
}
