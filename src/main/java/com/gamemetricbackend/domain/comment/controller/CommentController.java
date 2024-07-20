package com.gamemetricbackend.domain.comment.controller;

import com.gamemetricbackend.domain.comment.dto.CreateCommentDto;
import com.gamemetricbackend.domain.comment.service.CommentService;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public void CreateComment(
        @AuthenticationPrincipal
        UserDetailsImpl userDetails ,
        @RequestBody CreateCommentDto createCommentDto){
        commentService.CreateComment(userDetails.getId(),createCommentDto.getBroadCastId(),createCommentDto.getComment());
    }
}
