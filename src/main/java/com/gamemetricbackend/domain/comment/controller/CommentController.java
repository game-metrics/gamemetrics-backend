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
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    // todo : (Important) the function is suppose to be on a Live chat using  Kafka or RabbitMQ and Store on the Dynamo DB
    // todo : (중요) 해당기능은 카프카나 Rabbit MQ 를 사용하여 실시간 대요량 채팅을 하고 최종적으로는 DynamoDb 에 저장 되는 방식으로 작업해야 합니다.
    @PostMapping
    public void CreateComment(
        @AuthenticationPrincipal
        UserDetailsImpl userDetails ,
        @RequestBody CreateCommentDto createCommentDto){
        commentService.CreateComment(userDetails.getId(),createCommentDto.getBroadCastId(),createCommentDto.getComment());
    }
}
