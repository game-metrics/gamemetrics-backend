package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryQuery {
    Optional<User> findPasswordById(Long id);

    Optional<User> findByEmail(String getEmail);

    Page<UserInfoResponseDto> SearchUsersByNickName(String nickName, Pageable pageable);
}
