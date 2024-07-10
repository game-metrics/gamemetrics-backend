package com.gamemetricbackend.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequestDto {
    String currentPassword;
    String newPassword;
}
