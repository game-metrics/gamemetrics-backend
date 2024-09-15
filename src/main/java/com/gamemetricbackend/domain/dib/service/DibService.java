package com.gamemetricbackend.domain.dib.service;

import com.gamemetricbackend.domain.dib.entity.Dib;
import com.gamemetricbackend.domain.dib.repository.DibRepository;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DibService {
    private  DibRepository dibRepository;

}
