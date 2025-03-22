package com.gamemetricbackend.domain.dib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(indexes = {@Index(name = "dib_index",columnList = "follower_Id,streamer_name")})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_Id", nullable = false)
    private Long followerId;

    // todo : need a better way to get streamer name, using less access to the DB and lesser query using dsl.
    @Column(name = "streamer_name", nullable = false)
    private String streamerName;

    public Follow(Long userid, String streamerName) {
        this.followerId=userid;
        this.streamerName=streamerName;
    }
}
