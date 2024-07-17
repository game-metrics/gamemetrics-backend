package com.gamemetricbackend.domain.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentResponseDto {
    private String comment;
    private Long userId;
    private Long broadCastId;
}
