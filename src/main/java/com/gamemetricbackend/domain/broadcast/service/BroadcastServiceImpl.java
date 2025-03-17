package com.gamemetricbackend.domain.broadcast.service;

import com.gamemetricbackend.domain.broadcast.dto.BroadCastResponseDto;
import com.gamemetricbackend.domain.broadcast.dto.BroadcastCreationDto;
import com.gamemetricbackend.domain.broadcast.dto.OnOffAirRequestDto;
import com.gamemetricbackend.domain.broadcast.dto.UpdateBroadcastDto;
import com.gamemetricbackend.domain.broadcast.entitiy.Broadcast;
import com.gamemetricbackend.domain.broadcast.repository.BroadcastRepository;
import com.gamemetricbackend.global.exception.UserNotMatchException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 방송 관련 비즈니스 로직을 처리하는 서비스 구현체입니다.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class BroadcastServiceImpl implements BroadcastService {

    @Value("${hls.url}")
    private String hlsUrl;

    private final BroadcastRepository broadcastRepository;

    /**
     * 방송 ID로 방송을 조회합니다.
     * @param id 방송 ID
     * @return Optional<Broadcast>
     */
    private Optional<Broadcast> findById(Long id) {
        return broadcastRepository.findById(id);
    }

    /**
     * 방송 제목으로 방송 리스트를 검색합니다.
     * @param title 방송 제목
     * @param pageable 페이지 정보
     * @return 방송 리스트 (페이지 형식)
     */
    @Override
    public Page<BroadCastResponseDto> findByTitle(String title, Pageable pageable) {
        return broadcastRepository.findByTitle(title, pageable);
    }

    /**
     * 새로운 방송을 생성합니다.
     * @param userid 사용자 ID
     * @param broadcastCreationDto 방송 생성 정보
     * @return 생성된 방송 정보
     */
    @Override
    public BroadCastResponseDto createBroadcast(Long userid, BroadcastCreationDto broadcastCreationDto) {
        return new BroadCastResponseDto(broadcastRepository.save(new Broadcast(userid, broadcastCreationDto)));
    }

    /**
     * 방송 정보를 수정합니다.
     * @param userId 사용자 ID
     * @param updateBroadcastDto 방송 수정 정보
     * @return 수정된 방송 정보
     * @throws UserNotMatchException 사용자 권한 불일치 예외
     */
    @Override
    @Transactional
    public BroadCastResponseDto updateBroadcast(Long userId, UpdateBroadcastDto updateBroadcastDto)
            throws UserNotMatchException {
        Broadcast broadcast = findById(updateBroadcastDto.getBroadCastId())
                .orElseThrow(() -> new NoSuchElementException("the broadcast is not findable"));
        broadcast.update(userId, updateBroadcastDto);
        return new BroadCastResponseDto(broadcast);
    }

    /**
     * 방송을 송출 시작(On Air) 상태로 변경합니다.
     * @param userId 사용자 ID
     * @param onOffAirRequestDto 방송 ID 정보
     * @return 변경된 방송 정보
     * @throws UserNotMatchException 사용자 권한 불일치 예외
     */
    @Override
    @Transactional
    public BroadCastResponseDto OnAirBroadcast(Long userId, OnOffAirRequestDto onOffAirRequestDto)
            throws UserNotMatchException, NoSuchElementException {
        Broadcast broadcast = findById(onOffAirRequestDto.getBroadcastId())
                .orElseThrow(() -> new NoSuchElementException("can not find the broadcast"));
        broadcast.turnOnAir(userId);
        return new BroadCastResponseDto(broadcast);
    }

    /**
     * 방송을 송출 중지(Off Air) 상태로 변경합니다.
     * @param userId 사용자 ID
     * @param onOffAirRequestDto 방송 ID 정보
     * @return 변경된 방송 정보
     * @throws UserNotMatchException 사용자 권한 불일치 예외
     */
    @Override
    @Transactional
    public BroadCastResponseDto OffAirBroadcast(Long userId, OnOffAirRequestDto onOffAirRequestDto)
            throws UserNotMatchException, NoSuchElementException {
        Broadcast broadcast = findById(onOffAirRequestDto.getBroadcastId())
                .orElseThrow(() -> new NoSuchElementException("can not find the broadcast"));
        broadcast.turnOffAir(userId);
        return new BroadCastResponseDto(broadcast);
    }

    /**
     * 전체 방송 리스트를 페이지 형식으로 반환합니다.
     * @param pageable 페이지 정보
     * @return 방송 리스트 (페이지 형식)
     */
    @Override
    public Page<BroadCastResponseDto> getBroadcastPage(Pageable pageable) {
        return broadcastRepository.getBroadcastPage(pageable);
    }

    /**
     * 방송이 실제로 송출 중인지(HLS 스트림 존재 여부) 확인합니다.
     * 존재하지 않을 경우 방송을 강제로 종료 처리합니다.
     * @param broadcastId 방송 ID
     * @return true: 방송 종료 상태, false: 방송 송출 중
     */
    @Override
    @Transactional
    public Boolean ConfirmBroadcast(Long broadcastId) {
        String url = hlsUrl + broadcastId + ".m3u8";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                // HLS 스트림이 존재함 => 방송 진행 중
                return false;
            } else {
                // 스트림 없음 => 방송 종료 처리
                log.error("방송 종료 확인 완료");
                Broadcast broadcast = findById(broadcastId)
                        .orElseThrow(() -> new NoSuchElementException("can not find the broadcast"));
                broadcast.turnOffAirForce();
                return true;
            }
        } catch (IOException e) {
            // 예외 발생 시 방송 종료로 간주
            log.error("서버연결 문제");
            Broadcast broadcast = findById(broadcastId)
                    .orElseThrow(() -> new NoSuchElementException("can not find the broadcast"));
            broadcast.turnOffAirForce();
            return true;
        }
    }
}
