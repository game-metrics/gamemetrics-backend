package com.gamemetricbackend.domain.dib.service;

import com.gamemetricbackend.domain.dib.entity.Dib;
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
        Dib dib;
        try {
            dib = dibRepository.findByFollowerIdAndStreamerName(userid,streamerName).orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException exception){
            dib = createNewDib(userid,streamerName);
        }
        dib.updateStatus();
        return dib.getStatus();
    }

    private Dib createNewDib(Long userid, String streamerName){
        Dib dib = new Dib(userid,streamerName);
        dibRepository.save(dib);
        dib.updateStatus();
        return dib;
    }
}
