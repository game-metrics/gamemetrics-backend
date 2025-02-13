package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.request.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    public Optional<User> findById(Long id);

    SignUpResponseDto signUp(SignupRequestDto requestDto);

    boolean UpdatePassword(Long userId, UpdatePasswordRequestDto updatePasswordRequestDto)
        throws NoSuchUserException;

    UserInfoResponseDto getProfile(Long id) throws NoSuchUserException;

    Page<UserInfoResponseDto> searchUser(String nickName, Pageable pageable);
}
