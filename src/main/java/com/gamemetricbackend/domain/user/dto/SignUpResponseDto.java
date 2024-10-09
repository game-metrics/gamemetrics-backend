package com.gamemetricbackend.domain.user.dto;

import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SignUpResponseDto {
    String username;
    String nickname;
    UserRoleEnum userRoleEnum;
}
