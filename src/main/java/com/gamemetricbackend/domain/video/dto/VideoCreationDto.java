package com.gamemetricbackend.domain.video.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public class VideoCreationDto {
    String title;
    String thumbNailUrl;
    String VideoUrl;

    public VideoCreationDto(String title, String imageUrl, String videoUrl) {
        this.title = title;
        this.thumbNailUrl = imageUrl;
        this.VideoUrl = videoUrl;
    }
}
