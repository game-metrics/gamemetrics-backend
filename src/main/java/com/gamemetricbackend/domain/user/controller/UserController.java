package com.gamemetricbackend.domain.user.controller;

import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
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
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDto<String>> signup(
        @Valid @RequestBody SignupRequestDto requestDto
    ){

        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success("account created"));
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
