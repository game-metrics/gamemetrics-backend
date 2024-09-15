package com.gamemetricbackend.domain.dib.service;

import com.gamemetricbackend.domain.dib.repository.DibRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibService {
    private  DibRepository dibRepository;

}
