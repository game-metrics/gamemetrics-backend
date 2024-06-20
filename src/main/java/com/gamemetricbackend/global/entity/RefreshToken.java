package com.gamemetricbackend.global.entity;

import jakarta.persistence.Column;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    @Column
    private String refreshToken;

    @Column
    private String previousAccessToken;

    @Column
    private Long userId;

    public void update(String accessToken) {
        this.previousAccessToken = accessToken.substring(7);
    }
}
