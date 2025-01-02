package com.gamemetricbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemetricbackend.domain.chat.websockettest.ChatMessage;
import com.gamemetricbackend.domain.chat.websockettest.ChatRoom;
import com.gamemetricbackend.domain.chat.websockettest.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    /**
     * 웹소켓 연결 성공시
     * @param session
     */

    private final ObjectMapper objectMapper;
    //payload를 ChatMessage 객체로 만들어 주기 위한 objectMapper

    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); //메세지를 가져오기
        log.info("{}", payload); //log 출력

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        //payload를 ChatMessage 객체로 만들어주기

        ChatRoom chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        //ChatMessage 객체에서 roomId를 가져와 일치하는 room 주입

        chatRoom.handlerActions(session, chatMessage, chatService);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }
}
