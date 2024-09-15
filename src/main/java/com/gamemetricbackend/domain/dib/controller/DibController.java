package com.gamemetricbackend.domain.dib.controller;

import com.gamemetricbackend.domain.dib.service.DibService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dib")
public class DibController {
    private DibService dibService;

}
