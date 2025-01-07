//package com.gamemetricbackend.domain.chat.websockettest;
//
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Controller
//@RequiredArgsConstructor
//@RestController
//@RequestMapping(value="/chat")
//public class chatTest {
//    // 출처는 여기서 https://spring.io/guides/gs/messaging-stomp-websocket
////    @GetMapping()
////    public String chat(Model model) {
////
////        model.addAttribute("userid", 1);
////
////        return "chat/chat";
////    }
//    private final ChatService chatService;
//    @PostMapping
//    public ChatRoom createRoom(@RequestBody String name) {
//        return chatService.createRoom(name);
//        //Post 요청이 들어올 시, Json에서 name 값을 받아 방을 생성한다.
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//        //Get 요청이 들어올 시, 모든 방 목록을 조회한다.
//    }
//}
