package com.gamemetricbackend.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OauthUserInfoDto {
    private Long id;
    private String nickname;
    private String email;

    public OauthUserInfoDto(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}
