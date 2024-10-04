package com.gamemetricbackend.domain.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BroadCastResponseDto {
    String id;
    String title;
    String thumbNailUrl;
}
