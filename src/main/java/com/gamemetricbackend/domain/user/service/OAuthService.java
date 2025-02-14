package com.gamemetricbackend.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamemetricbackend.global.dto.LoginResponseDto;
import java.util.Map;

/**
 * OAuth 인증 서비스를 처리하는 인터페이스입니다.
 * 카카오 및 구글 인증을 처리하는 메서드를 제공합니다.
 */
public interface OAuthService {

    /**
     * 카카오 인증을 처리합니다.
     *
     * @param requestBody 카카오 인증 요청에 필요한 데이터가 포함된 Map
     * @return 카카오 인증에 성공한 후 반환되는 로그인 응답 정보 (LoginResponseDto)
     * @throws JsonProcessingException JSON 처리 중 발생할 수 있는 예외
     */
    LoginResponseDto KakaoAuth(Map<String, String> requestBody) throws JsonProcessingException;

    /**
     * 구글 인증을 처리합니다.
     *
     * @param requestBody 구글 인증 요청에 필요한 데이터가 포함된 Map
     * @return 구글 인증에 성공한 후 반환되는 로그인 응답 정보 (LoginResponseDto)
     * @throws JsonProcessingException JSON 처리 중 발생할 수 있는 예외
     */
    LoginResponseDto GoogleAuth(Map<String, String> requestBody) throws JsonProcessingException;
}
