package com.gamemetricbackend.domain.video.controller;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoCreationDto;
import com.gamemetricbackend.domain.video.dto.VideoResponseDto;
import com.gamemetricbackend.domain.video.dto.VideoUpdateDto;
import com.gamemetricbackend.domain.video.service.VideoService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.exception.UserNotMatchException;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 영상 관련 기능을 처리하는 컨트롤러 클래스입니다.
 * 영상 생성, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    /**
     * 영상을 생성하는 API입니다.
     *
     * @param userDetails 로그인한 사용자 정보
     * @param videoCreationDto 영상 생성에 필요한 제목, 썸네일, URL 등의 정보를 담은 DTO
     * @return 생성된 영상 정보를 포함한 ResponseEntity
     */
    @PostMapping
    public ResponseEntity<ResponseDto<VideoResponseDto>> CreateVideo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody VideoCreationDto videoCreationDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(videoService.createVideo(userDetails.getId(), videoCreationDto)));
    }

    /**
     * 기존 영상을 수정하는 API입니다. (제목, 썸네일 변경)
     *
     * @param userDetails 로그인한 사용자 정보
     * @param videoUpdateDto 영상 수정에 필요한 정보(제목, 썸네일)를 담은 DTO
     * @param videoId 수정할 영상 ID
     * @return 수정된 영상 정보를 포함한 ResponseEntity
     * @throws UserNotMatchException 영상 소유자가 아닌 경우 발생하는 예외
     */
    @PutMapping("/{videoId}")
    public ResponseEntity<ResponseDto<VideoResponseDto>> UpdateVideo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody VideoUpdateDto videoUpdateDto,
            @PathVariable(name = "videoId") Long videoId
    ) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(videoService.updateVideo(userDetails.getId(), videoUpdateDto, videoId)));
    }

    /**
     * 영상을 삭제하는 API입니다.
     *
     * @param userDetails 로그인한 사용자 정보
     * @param videoId 삭제할 영상 ID
     * @return 삭제 성공 여부(Boolean)를 포함한 ResponseEntity
     * @throws UserNotMatchException 영상 소유자가 아닌 경우 발생하는 예외
     */
    @DeleteMapping("/{videoId}")
    public ResponseEntity<ResponseDto<Boolean>> DeleteVideo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(name = "videoId") Long videoId
    ) throws UserNotMatchException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(videoService.deleteVideo(userDetails.getId(), videoId)));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Page<VideoResponseDto>>> getBroadcastPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(videoService.getVideoPage(pageable)));
    }
}
