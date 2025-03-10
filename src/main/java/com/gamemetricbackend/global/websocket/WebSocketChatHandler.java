package com.gamemetricbackend.global.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemetricbackend.global.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketChatHandler는 Spring WebSocket을 통해 실시간 채팅 기능을 처리하는 핸들러입니다.
 * 채팅방별 세션 관리를 통해 JOIN 및 TALK 메시지를 처리하고,
 * 해당 방의 사용자들에게 메시지를 브로드캐스팅합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    /** JSON 직렬화/역직렬화를 위한 ObjectMapper */
    private final ObjectMapper mapper;

    /**
     * 채팅방 ID별 WebSocket 세션 리스트를 관리하는 맵.
     * 각 채팅방마다 연결된 사용자 세션을 Set으로 저장.
     */
    private final Map<Long, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    /**
     * 전체 WebSocket 세션을 세션 ID로 관리하는 맵.
     * 클라이언트 개별 식별 및 연결 상태 추적 용도.
     */
    private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    /**
     * 클라이언트가 WebSocket에 최초 연결 시 호출됩니다.
     *
     * @param session 연결된 WebSocket 세션
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessionMap.put(session.getId(), session);
    }

    /**
     * 클라이언트로부터 메시지를 수신했을 때 호출됩니다.
     * 메시지 타입에 따라 JOIN 또는 TALK 처리 로직을 분기합니다.
     *
     * @param session 메시지를 보낸 WebSocket 세션
     * @param message 수신된 텍스트 메시지
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("수신한 메시지: {}", payload);

        // 메시지를 Map 형식으로 파싱
        Map<String, Object> data = mapper.readValue(payload, HashMap.class);
        String type = (String) data.get("type");

        if ("JOIN".equals(type) || "TALK".equals(type)) {
            // 채팅 메시지를 DTO로 변환 후 처리
            ChatMessageDto chatMessageDto = mapper.convertValue(data, ChatMessageDto.class);
            handleChatMessage(session, chatMessageDto);
        } else {
            log.error("알 수 없는 메시지 타입: {}", type);
        }
    }

    /**
     * 채팅 메시지를 처리하고 해당 채팅방의 모든 사용자에게 메시지를 전송합니다.
     *
     * @param session 보낸 사용자 세션
     * @param chatMessageDto 채팅 메시지 DTO
     * @throws Exception 예외 발생 시 처리
     */
    private void handleChatMessage(WebSocketSession session, ChatMessageDto chatMessageDto) throws Exception {
        // 채팅방에 사용자 세션 등록
        Set<WebSocketSession> roomSessions = chatRoomSessionMap
                .computeIfAbsent(chatMessageDto.getRoomId(), k -> new HashSet<>());
        roomSessions.add(session);

        // 메시지를 JSON 문자열로 직렬화
        String jsonMessage = mapper.writeValueAsString(chatMessageDto);

        // 같은 채팅방 사용자 모두에게 메시지 전송
        for (WebSocketSession webSocketSession : roomSessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

    /**
     * 클라이언트가 WebSocket 연결을 종료했을 때 호출됩니다.
     * 해당 세션을 모든 채팅방 및 전체 세션 목록에서 제거합니다.
     *
     * @param session 종료된 WebSocket 세션
     * @param status 종료 상태
     * @throws Exception 예외 발생 시 처리
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 종료", session.getId());
        sessionMap.remove(session.getId());

        // 모든 채팅방에서 해당 세션 제거
        chatRoomSessionMap.values().forEach(roomSessions -> roomSessions.remove(session));
    }
}
