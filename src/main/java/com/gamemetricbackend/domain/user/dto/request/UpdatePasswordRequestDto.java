package com.gamemetricbackend.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordRequestDto {
    String currentPassword;
    @Size(max = 50,message = "password maximum length is 50")
    @NotBlank(message = "password is compulsory")
    String newPassword;
}
