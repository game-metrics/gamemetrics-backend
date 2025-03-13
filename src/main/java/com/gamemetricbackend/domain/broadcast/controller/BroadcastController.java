package com.gamemetricbackend.domain.broadcast.controller;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OnOffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.service.BroadcastService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/broadcasts")
public class BroadcastController {

    private final BroadcastService broadcastService;

    @PostMapping
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> createBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody BroadcastCreationDto broadcastCreationDto
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.createBroadcast(userDetails.getId(),broadcastCreationDto)));
}

    @Operation(summary = "방송 검색", description = "방송를 검색한다.")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<Page<BroadCastResponseDto>>> findBroadcastByTitle(
        @RequestParam(name = "title") String title, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.findByTitle(title, pageable)));
    }

    @Operation(summary = "방송 리스트(페이지) 불러오기", description = "방송를 리스트(페이지) 를 불러온다.")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<BroadCastResponseDto>>> getBroadcastPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.getBroadcastList(pageable)));
    }

    @PutMapping
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> updateBroadcast(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody UpdateBroadcastDto updateBroadcastDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.updateBroadcast(userDetails.getId(),updateBroadcastDto)));
    }

    @PatchMapping("/on")
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> OnAirBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody OnOffAirRequestDto onOffAirRequestDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.OnAirBroadcast(userDetails.getId(),onOffAirRequestDto)));
    }

    @PatchMapping("/off")
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> OffAirBroadcast(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody OnOffAirRequestDto onOffAirRequestDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.OffAirBroadcast(userDetails.getId(),onOffAirRequestDto)));
    }
}
