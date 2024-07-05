package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.token.key}")
    String adminToken;

    @Override
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public void signup(SignupRequestDto requestDto) {
        if(requestDto.getAdminToken().equals(adminToken)){
            requestDto.setAdmin(true);
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(new User(requestDto));
    }
}
