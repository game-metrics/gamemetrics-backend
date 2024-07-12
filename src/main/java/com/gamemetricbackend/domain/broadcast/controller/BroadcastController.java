package com.gamemetricbackend.domain.broadcast.controller;

import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.service.BroadcastService;
import com.gamemetricbackend.domain.broadcast.service.BroadcastServiceImpl;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/broadcast")
public class BroadcastController {

    private final BroadcastService broadcastService;

    @GetMapping
    private ResponseEntity<ResponseDto<Broadcast>> findBroadcast(@RequestParam String title){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(broadcastService.findByTitle(title)));
    }

}
