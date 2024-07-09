package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;

public class UserRepositoryQueryImpl implements UserRepositoryQuery{

    @Override
    public Optional<User> findPasswordById(Long id) {

        return Optional.empty();
    }
}
