package com.gamemetricbackend.domain.comment.service;

import com.gamemetricbackend.domain.comment.repository.ChattingS3Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private ChattingS3Logger chattingS3Logger;

    @Override
    public Boolean CreateChat(Long id, Long broadcastId,String comment) {
        return false;
        //todo : this is just temporal the chats will be live on a kafka then be stored into a dynamo DB
    }
}
