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

/**
 * UserController는 사용자 관련 API를 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User Controller Swagger", description = "Response Estimate API")
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;

    /**
     * 새로운 사용자 계정을 생성합니다.
     *
     * @param requestDto 회원가입 요청 데이터
     * @return 회원가입 결과를 포함한 ResponseEntity
     */
    @PostMapping
    @Operation(summary = "계정생성", description = "계정을 생성한다.")
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignupRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.signUp(requestDto)));
    }

    /**
     * 현재 로그인한 사용자의 프로필 정보를 조회합니다.
     *
     * @param userDetails 로그인한 사용자 정보
     * @return 사용자 프로필 정보
     * @throws NoSuchUserException 사용자가 존재하지 않을 경우 예외 발생
     */
    @Operation(summary = "개인정보 가져오기", description = "유저가 본인 정보를 가져온다")
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails)
        throws NoSuchUserException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.getProfile(userDetails.getUser().getId())));
    }

    /**
     * 닉네임을 기준으로 사용자를 검색합니다.
     *
     * @param nickName 검색할 사용자 닉네임
     * @param pageable 페이징 정보
     * @return 검색된 사용자 목록을 포함한 ResponseEntity
     */
    @Operation(summary = "유저검색", description = "유저를 검색한다")
    @GetMapping()
    public ResponseEntity<ResponseDto<Page<UserInfoResponseDto>>> searchUser(@RequestParam String nickName, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.searchUser(nickName, pageable)));
    }

    /**
     * 사용자의 비밀번호를 변경합니다.
     *
     * @param updatePasswordRequestDto 비밀번호 변경 요청 데이터
     * @param userDetails 로그인한 사용자 정보
     * @return 비밀번호 변경 성공 여부
     * @throws NoSuchUserException 사용자가 존재하지 않을 경우 예외 발생
     */
    @Operation(summary = "비번변경", description = "비밀번호를 변경한다.")
    @PatchMapping
    public ResponseEntity<ResponseDto<Boolean>> changePassword(
        @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws NoSuchUserException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.UpdatePassword(userDetails.getUser().getId(), updatePasswordRequestDto)));
    }

    /**
     * 카카오 로그인을 처리합니다.
     *
     * @param requestBody 카카오 로그인 요청 데이터
     * @return 로그인 응답을 포함한 ResponseEntity
     * @throws JsonProcessingException JSON 처리 중 예외 발생 가능
     */
    @Operation(summary = "Kakao Login", description = "Kakao login")
    @PostMapping("/login/kakao")
    public ResponseEntity<ResponseDto<LoginResponseDto>> KakaoLogin(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(oAuthService.KakaoAuth(requestBody)));
    }

    /**
     * 구글 로그인을 처리합니다.
     *
     * @param requestBody 구글 로그인 요청 데이터
     * @return 로그인 응답을 포함한 ResponseEntity
     * @throws JsonProcessingException JSON 처리 중 예외 발생 가능
     */
    @Operation(summary = "Google Login", description = "Google login")
    @PostMapping("/login/google")
    public ResponseEntity<ResponseDto<LoginResponseDto>> GoogleLogin(@RequestBody Map<String, String> requestBody) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(oAuthService.GoogleAuth(requestBody)));
    }
}
