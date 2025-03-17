package com.gamemetricbackend.domain.video.entitiy;

import com.gamemetricbackend.domain.video.dto.VideoCreationDto;
import com.gamemetricbackend.domain.video.dto.VideoUpdateDto;
import com.gamemetricbackend.global.entity.TimeStamped;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "video")
@NoArgsConstructor
public class Video extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long userId;

    @Column
    private String thumbNailUrl;

    @Column(nullable = false)
    private String videoUrl;

    public Video(Long userid, VideoCreationDto videoCreationDto) {
        // user id
        this.userId = userid;
        //dto
        this.title = videoCreationDto.getTitle();
        this.thumbNailUrl = videoCreationDto.getThumbNailUrl();
        this.videoUrl = videoCreationDto.getVideoUrl();
    }

    public void update(Long userid, VideoUpdateDto videoUpdateDto) throws UserNotMatchException {
        if(this.userId != userid){
            throw new UserNotMatchException("User does not have permission");
        }
        this.title = videoUpdateDto.getTitle();
        this.thumbNailUrl = videoUpdateDto.getThumbNailUrl();
    }
}
