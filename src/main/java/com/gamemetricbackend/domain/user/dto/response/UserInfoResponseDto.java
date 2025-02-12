package com.gamemetricbackend.domain.user.dto.response;

import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;

public class UserInfoResponseDto {
    private String email;
    private String nickname;
    private UserRoleEnum admin;

    public UserInfoResponseDto(User user){
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.admin = user.getRole();
    }
}
