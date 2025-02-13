package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.dto.temporal.Authority;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.request.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        User user = userRepository.save(new User(requestDto));

        return new SignUpResponseDto(user.getEmail(),user.getEmail());
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

    @Override
    public UserInfoResponseDto getProfile(Long id) throws NoSuchUserException {
        // 토큰에는 유저는 id 랑 role 만 가지고 있다.
        return new UserInfoResponseDto(userRepository.findById(id).orElseThrow(NoSuchUserException::new));
    }

    @Override
    public Page<UserInfoResponseDto> searchUser(String nickName, Pageable pageable) {
        return userRepository.SearchUsersByNickName(nickName,pageable);
    }
}
