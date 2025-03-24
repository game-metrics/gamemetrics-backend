package com.gamemetricbackend.domain.follow.controller;

import com.gamemetricbackend.domain.follow.dto.FollowResponseDto;
import com.gamemetricbackend.domain.follow.service.FollowService;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    // Follow 비즈니스 로직을 처리하는 서비스
    private final FollowService followService;

    /**
     * [POST] /follows
     * 특정 스트리머를 팔로우하거나 언팔로우합니다 (토글 방식).
     * - 로그인한 사용자의 ID와 streamerName을 받아서 처리합니다.
     * - 성공 시: true (팔로우), false (언팔로우)
     *
     * @param userDetails 로그인된 사용자 정보 (SecurityContext로부터 주입)
     * @param StreamerName 팔로우할 스트리머 이름 (RequestParam으로 전달됨)
     * @return ResponseEntity<Boolean> 결과 (true/false), 상태코드 201 CREATED
     */
    @PostMapping
    public ResponseEntity<Boolean> UpdateFollow(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(name = "streamerName") String StreamerName
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(followService.updateFollow(userDetails.getId(), StreamerName));
    }

    /**
     * [GET] /follows
     * 내가 팔로우한 스트리머 목록을 페이징하여 조회합니다.
     *
     * @param userDetails 로그인된 사용자 정보
     * @param pageable 페이징 정보 (page, size 등)
     * @return ResponseEntity<Page<FollowResponseDto>> 팔로우한 스트리머 목록
     */
    @GetMapping
    public ResponseEntity<Page<FollowResponseDto>> GetMyFollowPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(followService.getMyFollowPage(userDetails.getId(), pageable));
    }
}
