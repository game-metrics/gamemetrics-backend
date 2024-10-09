package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import java.util.Optional;

public interface UserService {
    public Optional<User> findById(Long id);

    SignUpResponseDto signUp(SignupRequestDto requestDto);

    boolean UpdatePassword(Long userId, UpdatePasswordRequestDto updatePasswordRequestDto)
        throws NoSuchUserException;
}
