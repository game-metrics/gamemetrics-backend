package com.gamemetricbackend.domain.dib.service;

import com.gamemetricbackend.domain.dib.entity.Follow;
import com.gamemetricbackend.domain.dib.repository.DibRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DibServiceImpl implements DibService{
    private final DibRepository dibRepository;

    // todo need a better way then using try catch...
    @Override
    @Transactional
    public Boolean upateDib(Long userid, String streamerName) {
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
