package com.gamemetricbackend.domain.follow.repository;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import java.util.Optional;

import com.gamemetricbackend.domain.follow.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long>,FollowRepositoryQuery {
    Optional<Follow> findByFollowerIdAndStreamerName(Long userId, String streamerName);

}
