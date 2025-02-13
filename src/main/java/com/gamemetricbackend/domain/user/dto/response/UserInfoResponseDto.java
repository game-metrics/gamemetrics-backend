package com.gamemetricbackend.domain.user.dto.response;

import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;

public class UserInfoResponseDto {
    private String email;
    private String nickname;
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
    public UserInfoResponseDto(User user){
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
