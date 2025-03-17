package com.gamemetricbackend.domain.video.dto;

import com.gamemetricbackend.domain.video.entitiy.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VideoResponseDto {
    Long id;
    String title;
    String thumbNailUrl;
    String videoUrl;

    public VideoResponseDto(Video video){
        this.id = video.getId();
        this.title = video.getTitle();
        this.thumbNailUrl = video.getThumbNailUrl();
        this.videoUrl = video.getVideoUrl();
    }
}
