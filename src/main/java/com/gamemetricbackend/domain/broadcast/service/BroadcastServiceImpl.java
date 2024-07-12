package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.repository.BroadcastRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService{
    private final BroadcastRepository broadcastRepository;

    public Optional<Broadcast> findById(Long id){
        return broadcastRepository.findById(id);
    }

    @Override
    public Broadcast findByTitle(String title) {
        return broadcastRepository.findByTitle(title);
    }

}
