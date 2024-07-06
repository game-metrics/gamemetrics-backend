package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;

public interface UserService {
    public Optional<User> findById(Long id);

    void signup(SignupRequestDto requestDto);
}
