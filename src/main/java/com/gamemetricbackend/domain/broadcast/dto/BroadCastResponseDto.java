package com.gamemetricbackend.domain.broadcast.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BroadCastResponseDto {
    String id;
    String title;
    String thumbNailUrl;
}
