package com.gamemetricbackend.domain.broadcast.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BroadCastResponseDto {
    Long id;
    String title;
    String thumbNailUrl;
    Long catagoryId;
}
