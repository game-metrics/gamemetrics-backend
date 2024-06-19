package com.gamemetricbackend.domain.user.repository;

import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById(Long id);
}
