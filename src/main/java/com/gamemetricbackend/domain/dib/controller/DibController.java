package com.gamemetricbackend.domain.dib.controller;

import com.gamemetricbackend.domain.dib.service.DibService;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.domain.user.service.UserServiceImpl;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import java.nio.file.attribute.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dibs")
public class DibController {
    private final DibService dibService;

    @PutMapping
    public ResponseEntity<Boolean> upateDib(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam(name = "streamerName") String StreamerName){
        return ResponseEntity.status(HttpStatus.CREATED).body(dibService.upateDib(userDetails.getId(),StreamerName));
    }
}
