package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;

public interface UserRepositoryQuery {
    Optional<User> findPasswordById(Long id);

    Optional<User> findByEmail(String getEmail);
}
