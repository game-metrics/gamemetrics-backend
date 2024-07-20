package com.gamemetricbackend.domain.comment.service;

public interface CommentService {

    void CreateComment(Long id, Long broadcastId,String comment);
}
