package com.gamemetricbackend.domain.broadcast.dto;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
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
    Long categoryId;

    public BroadCastResponseDto(Broadcast broadcast){
        this.id = broadcast.getId();
        this.title = broadcast.getTitle();
        this.thumbNailUrl = broadcast.getThumbNailUrl();
        this.categoryId = broadcast.getCategoryId();
    }
}
