package com.gamemetricbackend.domain.broadcast.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBroadcastDto {
    Long broadCastId;
    String title;
    String thumbNail;
}
