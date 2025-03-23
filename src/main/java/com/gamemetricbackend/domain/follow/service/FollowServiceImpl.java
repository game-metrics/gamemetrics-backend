package com.gamemetricbackend.domain.follow.service;

import com.gamemetricbackend.domain.follow.entity.Follow;
import java.util.NoSuchElementException;

import com.gamemetricbackend.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{
    private final FollowRepository dibRepository;

    // todo need a better way then using try catch...
    @Override
    @Transactional
    public Boolean upateFollow(Long userid, String streamerName) {
        try {
            dibRepository.delete(dibRepository.findByFollowerIdAndStreamerName(userid,streamerName).orElseThrow(NoSuchElementException::new));
        }catch (NoSuchElementException exception){
            createNewDib(userid,streamerName);
        }
        return Boolean.TRUE;
    }



    private Follow createNewDib(Long userid, String streamerName){
        Follow dib = new Follow(userid,streamerName);
        dibRepository.save(dib);
        return dib;
    }
}
