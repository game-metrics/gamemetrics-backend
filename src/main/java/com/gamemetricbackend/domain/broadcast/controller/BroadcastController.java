package com.gamemetricbackend.domain.broadcast.controller;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OnOffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.service.BroadcastService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 방송 관련 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/broadcasts")
public class BroadcastController {

    private final BroadcastService broadcastService;

    /**
     * 방송을 생성합니다.
     *
     * @param userDetails 현재 인증된 사용자 정보
     * @param broadcastCreationDto 방송 생성 요청 정보
     * @return 생성된 방송 정보
     * @throws IOException 방송 생성 중 I/O 예외 발생 시
     */
    @PostMapping
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> createBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody BroadcastCreationDto broadcastCreationDto
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.createBroadcast(userDetails.getId(), broadcastCreationDto)));
    }

    /**
     * 방송 제목으로 검색합니다.
     *
     * @param title    검색할 방송 제목
     * @param pageable 페이징 정보
     * @return 검색된 방송 리스트 (페이지 형태)
     */
    @Operation(summary = "방송 검색", description = "방송를 검색한다.")
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<Page<BroadCastResponseDto>>> findBroadcastByTitle(
            @RequestParam(name = "title") String title,
            @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.findByTitle(title, pageable)));
    }

    /**
     * 방송 리스트를 페이지 형식으로 불러옵니다.
     *
     * @param pageable 페이징 정보
     * @return 방송 리스트 (페이지 형태)
     */
    @Operation(summary = "방송 리스트(페이지) 불러오기", description = "방송를 리스트(페이지) 를 불러온다.")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<BroadCastResponseDto>>> getBroadcastPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.getBroadcastList(pageable)));
    }

    /**
     * 방송 정보를 수정합니다.
     *
     * @param userDetails         현재 인증된 사용자 정보
     * @param updateBroadcastDto 방송 수정 요청 정보
     * @return 수정된 방송 정보
     * @throws UserNotMatchException 사용자 불일치 예외
     */
    @PutMapping
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> updateBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateBroadcastDto updateBroadcastDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.updateBroadcast(userDetails.getId(), updateBroadcastDto)));
    }

    /**
     * 방송을 송출(On Air) 상태로 변경합니다.
     *
     * @param userDetails       현재 인증된 사용자 정보
     * @param onOffAirRequestDto 송출 시작 요청 정보
     * @return 변경된 방송 정보
     * @throws UserNotMatchException 사용자 불일치 예외
     */
    @PatchMapping("/on")
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> OnAirBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OnOffAirRequestDto onOffAirRequestDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.OnAirBroadcast(userDetails.getId(), onOffAirRequestDto)));
    }

    /**
     * 방송을 송출 중단(Off Air) 상태로 변경합니다.
     *
     * @param userDetails       현재 인증된 사용자 정보
     * @param onOffAirRequestDto 송출 종료 요청 정보
     * @return 변경된 방송 정보
     * @throws UserNotMatchException 사용자 불일치 예외
     */
    @PatchMapping("/off")
    public ResponseEntity<ResponseDto<BroadCastResponseDto>> OffAirBroadcast(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody OnOffAirRequestDto onOffAirRequestDto) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.OffAirBroadcast(userDetails.getId(), onOffAirRequestDto)));
    }

    /**
     * 방송이 송출 중인지 확인합니다.
     *
     * @param broadcastId 방송 ID
     * @return 방송 송출 여부 (true = 송출 중, false = 송출 아님)
     */
    @GetMapping("/confirm")
    public ResponseEntity<ResponseDto<Boolean>> ConfirmBroadcast(
            @RequestParam(name = "broadcastId") Long broadcastId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(broadcastService.ConfirmBroadcast(broadcastId)));
    }
}
