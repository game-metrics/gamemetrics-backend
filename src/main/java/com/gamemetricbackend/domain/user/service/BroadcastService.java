package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BroadcastService {
    @Autowired
    private final UserRepository userRepository;

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

}
