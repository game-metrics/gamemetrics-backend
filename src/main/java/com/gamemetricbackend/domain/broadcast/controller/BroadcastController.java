package com.gamemetricbackend.domain.broadcast.controller;

import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.service.BroadcastService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/broadcasts")
public class BroadcastController {

    private final BroadcastService broadcastService;

    @PostMapping
    public ResponseEntity<ResponseDto<Broadcast>> createBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ,@RequestBody BroadcastCreationDto broadcastCreationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.createBroadcast(userDetails.getId(),broadcastCreationDto)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseDto<Page<Broadcast>>> findBroadcastByTitle(
        @RequestParam(name = "title") String title, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.findByTitle(title, pageable)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Page<BroadcastCreationDto>>> getBroadcastPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.getBroadcastList(pageable)));
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Broadcast>> updateBroadcast(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody UpdateBroadcastDto updateBroadcastDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.updateBroadcast(userDetails.getId(),updateBroadcastDto)));
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<Broadcast>> OffAirBroadcast(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody OffAirRequestDto offAirRequestDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.OffAirBroadcast(userDetails.getId(),offAirRequestDto)));
    }
}
