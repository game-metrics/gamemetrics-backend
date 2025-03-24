package com.gamemetricbackend.domain.follow.service;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import com.gamemetricbackend.domain.follow.entity.Follow;
import java.util.NoSuchElementException;

import com.gamemetricbackend.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{
    private final FollowRepository followRepository;

    // todo need a better way then using try catch...
    @Override
    @Transactional
    public Boolean updateFollow(Long userid, String streamerName) {
        try {
            followRepository.delete(followRepository.findByFollowerIdAndStreamerName(userid,streamerName).orElseThrow(NoSuchElementException::new));
        }catch (NoSuchElementException exception){
            createNewDib(userid,streamerName);
        }
        return Boolean.TRUE;
    }

    @Override
    public Page<FollowResponseDto> getMyFollowPage(Long userId, Pageable pageable) {
        return followRepository.getFollowPage(userId,pageable);
    }


    private Follow createNewDib(Long userid, String streamerName){
        Follow dib = new Follow(userid,streamerName);
        followRepository.save(dib);
        return dib;
    }
}
