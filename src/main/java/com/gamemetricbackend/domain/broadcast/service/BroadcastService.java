package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import java.util.Optional;

public interface BroadcastService {
    Optional<Broadcast> findById(Long id);
}
