package com.gamemetricbackend.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamemetricbackend.global.dto.LoginResponseDto;
import java.util.Map;

public interface OAuthService {
    LoginResponseDto KakaoAuth(Map<String, String> requestBody) throws JsonProcessingException;

    LoginResponseDto GoogleAuth(Map<String, String> requestBody) throws JsonProcessingException;
}
