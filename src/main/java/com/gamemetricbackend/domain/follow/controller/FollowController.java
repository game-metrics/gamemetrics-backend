package com.gamemetricbackend.domain.follow.controller;

import com.gamemetricbackend.domain.follow.service.FollowService;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;

    @GetMapping
    public ResponseEntity<Boolean> upateFollow(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(name = "streamerName") String StreamerName){
        return ResponseEntity.status(HttpStatus.CREATED).body(followService.upateFollow(userDetails.getId(),StreamerName));
    }

    // get my dibs

    //


}
