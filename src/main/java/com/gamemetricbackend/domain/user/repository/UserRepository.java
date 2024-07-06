package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryQuery {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
