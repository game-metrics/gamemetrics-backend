package com.gamemetricbackend.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.request.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.service.OAuthService;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.dto.LoginResponseDto;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller Swagger", description = "Response Estimate API")
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;

    @PostMapping
    @Operation(summary = "계정생성", description = "계정를 생성한다.")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignupRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.signUp(requestDto)));
    }

    @Operation(summary = "개인정보 가져오기", description = "유저가 본인 정보를 가져온다")
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails)
        throws NoSuchUserException {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.getProfile(userDetails.getUser().getId())));
    }

    @Operation(summary = "유저검색", description = "유저를 검색한다")
    @GetMapping()
    public ResponseEntity<ResponseDto<Page<UserInfoResponseDto>>> searchUser(@RequestParam String nickName,@PageableDefault Pageable pageable){

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.searchUser(nickName,pageable)));
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
    @PostMapping("/login/kakao")
    public ResponseEntity<ResponseDto<LoginResponseDto>> KakaoLogin(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(oAuthService.KakaoAuth(requestBody)));
    }

    @Operation(summary = "google Login", description = "google login")
    @PostMapping("/login/google")
    public ResponseEntity<ResponseDto<LoginResponseDto>> GoogleLogin(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(oAuthService.GoogleAuth(requestBody)));
    }
}
