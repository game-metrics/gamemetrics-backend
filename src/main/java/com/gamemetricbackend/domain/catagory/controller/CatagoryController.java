package com.gamemetricbackend.domain.catagory.controller;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.catagory.dto.CatagoryCreationDto;
import com.gamemetricbackend.domain.catagory.dto.CatagoryResponseDto;
import com.gamemetricbackend.domain.catagory.entity.Catagory;
import com.gamemetricbackend.domain.catagory.service.CatagoryService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catagory")
@RequiredArgsConstructor
public class CatagoryController {
    private final CatagoryService catagoryService;
    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> createCatagory(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody CatagoryCreationDto catagoryCreationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(
            catagoryService.create(catagoryCreationDto,userDetails.getRole())));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CatagoryResponseDto>>> getCatagoryList(){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(
            catagoryService.getCatagoryList()));
    }
}
