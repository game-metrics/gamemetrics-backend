package com.gamemetricbackend.domain.broadcast.repository;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BroadcastRepository extends JpaRepository<Broadcast,Long> {
    Optional<Broadcast> findById(Long id);
}
