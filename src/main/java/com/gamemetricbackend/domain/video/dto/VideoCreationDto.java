package com.gamemetricbackend.domain.video.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class VideoCreationDto {
    String title;
    String thumbNailUrl;
    String videoUrl;

    public VideoCreationDto(String title, String imageUrl, String videoUrl) {
        this.title = title;
        this.thumbNailUrl = imageUrl;
        this.videoUrl = videoUrl;
    }
}
