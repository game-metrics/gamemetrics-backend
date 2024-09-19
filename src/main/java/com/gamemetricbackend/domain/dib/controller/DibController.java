package com.gamemetricbackend.domain.dib.controller;

import com.gamemetricbackend.domain.dib.service.DibService;
import com.gamemetricbackend.domain.user.service.UserService;
import com.gamemetricbackend.domain.user.service.UserServiceImpl;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import java.nio.file.attribute.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dibs")
public class DibController {
    private DibService dibService;

    @GetMapping
    public Boolean upateDib(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable(value = "streamerName") String StreamerName){
        return dibService.upateDib(userDetails.getId(),StreamerName);
    }

}
