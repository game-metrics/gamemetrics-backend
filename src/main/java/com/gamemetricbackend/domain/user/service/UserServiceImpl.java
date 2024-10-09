package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.temporal.Authority;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token.key}")
    String adminToken;

    @Override
    public Optional<User> findById(
        Long id
    ){
        return userRepository.findById(id);
    }

    @Override
    public SignUpResponseDto signUp(
        SignupRequestDto requestDto) {
        if(requestDto.getAdminToken().equals(adminToken)){
            requestDto.setAdmin(true);
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        // todo : remove all below after the assignment check.
        User user = userRepository.save(new User(requestDto));

        Authority authorityName = new Authority(user.getRole());

        List<Authority> Authority = new ArrayList<>();
        Authority.add(authorityName);

        return new SignUpResponseDto(user.getUsername(),user.getUsername(),Authority);
    }

    @Override
    @Transactional
    public boolean UpdatePassword(
        Long userId,
        UpdatePasswordRequestDto updatePasswordRequestDto
    )
        throws NoSuchUserException {
        User user = userRepository.findPasswordById(userId).orElseThrow(NoSuchUserException::new);

        if(passwordEncoder.matches(updatePasswordRequestDto.getCurrentPassword(),user.getPassword())){
            return user.UpdatePassword(passwordEncoder.encode(updatePasswordRequestDto.getNewPassword()));
        }
        return false;
    }
}
