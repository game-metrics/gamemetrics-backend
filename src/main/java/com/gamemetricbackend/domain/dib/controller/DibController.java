package com.gamemetricbackend.domain.dib.controller;

import com.gamemetricbackend.domain.dib.entity.Dib;
import com.gamemetricbackend.domain.dib.service.DibService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dib")
public class DibController {
    private DibService dibService;

    public ResponseEntity<ResponseDto<Page<Dib>>> getDibs(@AuthenticationPrincipal
    UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.success(dibService.getPageDibs(userDetails.getId())));
    }

}
