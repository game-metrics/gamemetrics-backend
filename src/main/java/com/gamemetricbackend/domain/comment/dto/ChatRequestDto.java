package com.gamemetricbackend.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChatRequestDto {
    private String comment;
    private Long broadCastId;
}
