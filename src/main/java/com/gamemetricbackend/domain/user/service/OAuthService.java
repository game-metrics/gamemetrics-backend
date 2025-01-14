package com.gamemetricbackend.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;

public interface OAuthService {
    public String KakaoAuth(Map<String, String> requestBody) throws JsonProcessingException;
}
