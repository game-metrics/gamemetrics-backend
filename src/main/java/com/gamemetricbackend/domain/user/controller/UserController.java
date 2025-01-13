package com.gamemetricbackend.domain.user.controller;

import com.gamemetricbackend.domain.user.dto.LoginRequestDto;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller Swagger", description = "Response Estimate API")
public class UserController {
    //todo : the whole login and jwt system might be moved onto a AWS Cognito User pool and Identity pool
    // 아마 학습 목적으로 aws 유저풀 같은 시스템으로 유저를 관리 할수 있습니다.
    private final UserService userService;

    @PostMapping
    @Operation(summary = "계정생성", description = "계정를 생성한다.")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignupRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.signUp(requestDto)));
    }

    @Operation(summary = "비번변경", description = "비번를 변경한다.")
    @PatchMapping
    public ResponseEntity<ResponseDto<Boolean>> changePassword(
        @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws NoSuchUserException {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.UpdatePassword(userDetails.getUser().getId(), updatePasswordRequestDto)));
    }

    @Operation(summary = "Kakao Login", description = "Kakao login")
    @PostMapping("/login/oauth2/callback/kakao")
    // AuththenticationFilter 에 이미 setFilterProcessesUrl("/users/login"); 해놨습니다.
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        return ResponseEntity.ok().build();
    }
}
