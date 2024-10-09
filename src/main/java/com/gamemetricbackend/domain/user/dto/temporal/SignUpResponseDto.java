package com.gamemetricbackend.domain.user.dto.temporal;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpResponseDto {
    private String username;
    private String nickname;
    // todo remove after the assignment confirmation
    private List<Authority> authorities;
}
