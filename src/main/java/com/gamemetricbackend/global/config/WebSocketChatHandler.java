package com.gamemetricbackend.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemetricbackend.domain.chat.websockettest.ChatMessageDto;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    Map<String, String> jsonMap = new HashMap<>();

    // 소켓 세션을 저장할 Set
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 ID와 소켓 세션을 저장할 Map
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
        jsonMap.put("status", "connection success");
        session.sendMessage(new TextMessage(mapper.writeValueAsString(jsonMap)));
    }

    // 소켓 메시지 처리
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload: {}", payload);

        // 클라이언트로부터 받은 메시지를 ChatMessageDto로 변환
        ChatMessageDto chatMessageDto = mapper.readValue(payload, ChatMessageDto.class);
        log.info("session: {}", chatMessageDto.toString());

        // 채팅방 세션 가져오기 (없으면 새로 생성)
        Set<WebSocketSession> roomSessions = chatRoomSessionMap.computeIfAbsent(chatMessageDto.getRoomId(), k -> new HashSet<>());

        // 메시지 타입에 따라 분기
        if (chatMessageDto.getType() == ChatMessageDto.MessageType.JOIN) {
            roomSessions.add(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 입장하셨습니다.");
        } else if (chatMessageDto.getType() == ChatMessageDto.MessageType.LEAVE) {
            roomSessions.remove(session);
            chatMessageDto.setMessage(chatMessageDto.getSender() + "님이 퇴장하셨습니다.");
        }

        // 채팅 메시지 전송 (빈 채팅방이면 전송하지 않음)
        if (!roomSessions.isEmpty()) {
            String jsonMessage = mapper.writeValueAsString(chatMessageDto);
            for (WebSocketSession webSocketSession : roomSessions) {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(jsonMessage));
                }
            }
        }
    }

    // 소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessions.remove(session);

        // 모든 채팅방에서 해당 세션 제거
        chatRoomSessionMap.values().forEach(roomSessions -> roomSessions.remove(session));
    }
}
