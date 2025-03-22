package com.gamemetricbackend.domain.dib.repository;

import java.util.Optional;

import com.gamemetricbackend.domain.dib.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>,FollowRepositoryQuery {
    Optional<Follow> findByFollowerIdAndStreamerName(Long userId, String streamerName);
}
