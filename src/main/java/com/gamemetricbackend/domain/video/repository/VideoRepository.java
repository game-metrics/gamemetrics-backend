package com.gamemetricbackend.domain.video.repository;

import com.gamemetricbackend.domain.video.entitiy.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video,Long>, VideoRepositoryQuery {
    Optional<Video> findById(Long id);
}
