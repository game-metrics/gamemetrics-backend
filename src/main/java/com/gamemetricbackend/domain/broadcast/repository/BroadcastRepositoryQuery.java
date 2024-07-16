package com.gamemetricbackend.domain.broadcast.repository;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BroadcastRepositoryQuery {
    Page<Broadcast> findByTitle(String title, Pageable pageable);
}
