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
        //todo : need a function that uploads comments on redis so it can be live
    }
}
