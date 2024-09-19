package com.gamemetricbackend.domain.comment.service;

import com.gamemetricbackend.domain.comment.entitiy.Comment;
import com.gamemetricbackend.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public void CreateComment(Long id, Long broadcastId,String comment) {
        commentRepository.save(new Comment(id,broadcastId,comment));
        //todo : this is just temporal the chats will be live on a kafka then be stored into a dynamo DB
    }
}
