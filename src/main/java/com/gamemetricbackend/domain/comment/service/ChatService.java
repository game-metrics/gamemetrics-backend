package com.gamemetricbackend.domain.comment.service;

public interface ChatService {

    Boolean CreateChat(Long id, Long broadcastId,String comment);
}
