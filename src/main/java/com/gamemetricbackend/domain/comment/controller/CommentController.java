package com.gamemetricbackend.domain.comment.controller;

import com.gamemetricbackend.domain.comment.service.CommentService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseDto<String>>CreateComment(){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.success(commentService.CreateComment()));
    }

}
