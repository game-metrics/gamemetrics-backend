package com.gamemetricbackend.domain.broadcast.entitiy;



import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;

import com.gamemetricbackend.global.entity.TimeStamped;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "broadcast")
@NoArgsConstructor
public class Broadcast extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long userId;

    @Column
    @Enumerated(EnumType.STRING)
    BroadcastStatus broadcastStaus;

    // todo thumbnails will be saved on s3? or somewhere better.
    @Column(nullable = false)
    private String thumbNailUrl;

    public Broadcast(Long userId,BroadcastCreationDto broadcastCreationDto) {
        this.title = broadcastCreationDto.getTitle();
        this.thumbNailUrl = broadcastCreationDto.getThumbNailUrl();
        this.userId = userId;
        this.broadcastStaus = BroadcastStatus.ONAIR;
    }

    public void update(Long userId,UpdateBroadcastDto updateBroadcastDto)
        throws UserNotMatchException {
        if(this.userId.equals(userId)){
            this.title = updateBroadcastDto.getTitle();
            this.thumbNailUrl = updateBroadcastDto.getThumbNail();
        }
        else {
            throw new UserNotMatchException();
        }
    }

    public void turnOffAir(Long userId)
        throws UserNotMatchException {
        if(this.userId.equals(userId)){
            this.broadcastStaus = BroadcastStatus.OFFAIR;
        }
        else {
            throw new UserNotMatchException();
        }
    }
}
