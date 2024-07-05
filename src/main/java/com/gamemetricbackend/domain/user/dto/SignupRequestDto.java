package com.gamemetricbackend.domain.user.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank(message = "username is compulsory")
    private String username;

    @NotBlank(message = "password is compulsory")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
