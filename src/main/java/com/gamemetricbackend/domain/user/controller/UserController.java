package com.gamemetricbackend.domain.user.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gamemetricbackend.domain.user.dto.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
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
public class UserController {
    //todo : the whole login and jwt system might be moved onto a AWS Cognito User pool and Identity pool
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto<SignUpResponseDto>> signUp(
        @Valid @RequestBody SignupRequestDto requestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.signUp(requestDto)));
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Boolean>> changePassword(
        @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws NoSuchUserException {

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(userService.UpdatePassword(userDetails.getUser().getId(), updatePasswordRequestDto)));
    }
}
