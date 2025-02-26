package com.gamemetricbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemetricbackend.global.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    // 채팅방 ID별 WebSocket 세션 관리
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessionMap.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("수신한 메시지: {}", payload);

        // 메시지 파싱
        Map<String, Object> data = mapper.readValue(payload, HashMap.class);
        String type = (String) data.get("type");

        if ("chat".equals(type)||"join".equals(type)) {
            // 채팅 메시지 처리
            ChatMessageDto chatMessageDto = mapper.convertValue(data, ChatMessageDto.class);
            handleChatMessage(session, chatMessageDto);
        }
        else {
            log.error("알수없는 type" + type);
        }
    }

    private void handleChatMessage(WebSocketSession session, ChatMessageDto chatMessageDto) throws Exception {
        Set<WebSocketSession> roomSessions = chatRoomSessionMap.computeIfAbsent(chatMessageDto.getRoomId(), k -> new HashSet<>());
        roomSessions.add(session);
        // 채팅 메시지 전송
        String jsonMessage = mapper.writeValueAsString(chatMessageDto);
        for (WebSocketSession webSocketSession : roomSessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 종료", session.getId());
        sessionMap.remove(session.getId());
        chatRoomSessionMap.values().forEach(roomSessions -> roomSessions.remove(session));
    }
}
