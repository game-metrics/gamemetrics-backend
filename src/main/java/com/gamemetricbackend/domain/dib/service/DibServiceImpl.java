package com.gamemetricbackend.domain.dib.service;

import com.gamemetricbackend.domain.dib.repository.DibRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibServiceImpl {
    private DibRepository dibRepository;
}
