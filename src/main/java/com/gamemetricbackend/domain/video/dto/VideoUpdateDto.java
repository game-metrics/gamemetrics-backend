package com.gamemetricbackend.domain.video.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VideoUpdateDto {
    String title;
    String thumbNailUrl;

    public VideoUpdateDto(String title, String imageUrl) {
        this.title = title;
        this.thumbNailUrl = imageUrl;
    }
}
