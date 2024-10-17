package com.gamemetricbackend.global.handler;

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
    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        try  {
            session.sendMessage(
                new TextMessage("웹소켓 연결 성공"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
