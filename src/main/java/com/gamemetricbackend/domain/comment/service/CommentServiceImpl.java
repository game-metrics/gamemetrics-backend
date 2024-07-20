package com.gamemetricbackend.domain.comment.service;

import com.gamemetricbackend.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;


    @Override
    public String CreateComment() {
        return ":";
    }
}
