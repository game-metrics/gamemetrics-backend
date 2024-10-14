package com.gamemetricbackend.domain.user.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

    @Size(max = 20,message = "username maximum length is 20")
    @NotBlank(message = "username is compulsory")
    private String username;

    @Size(max = 50,message = "password maximum length is 50")
    @NotBlank(message = "password is compulsory")
    private String password;

    @Size(max = 20,message = "nickname maximum length is 20")
    @NotBlank(message = "nickname is compulsory")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}
