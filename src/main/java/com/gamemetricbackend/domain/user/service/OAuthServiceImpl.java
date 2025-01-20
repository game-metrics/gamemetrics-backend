package com.gamemetricbackend.domain.user.service;

import com.esotericsoftware.minlog.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.global.dto.OauthUserInfoDto;
import com.gamemetricbackend.global.util.JwtUtil;
import java.net.URI;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OAuthServiceImpl implements OAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    @Value("${kakao.client.id}")
    private String kakaoClientId;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${google.client.id}")
    private String googleClientId;

    @Value("${google.client.password}")
    private String googleClientPassword;

    @Value("${google.redirect.url}")
    private String googleRedirectUri;

    @Value("${google.user.info.url}")
    private String userInfoUrl;

    // todo : still thinking whether if i need to move the auth url on application.properties (git secret)
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";

    public OAuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplateBuilder restTemplateBuilder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String KakaoAuth(Map<String, String> requestBody) throws JsonProcessingException {
        String code = requestBody.get("code");
        String accessToken = getToken(code, "https://kauth.kakao.com", "/oauth/token", kakaoClientId, kakaoRedirectUri);
        OauthUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        User kakaoUser = registerUserIfNeeded(kakaoUserInfo.getEmail(), kakaoUserInfo.getNickname());
        return jwtUtil.createToken(kakaoUser.getId(), kakaoUser.getRole());
    }

    @Override
    public String GoogleAuth(Map<String, String> requestBody) throws JsonProcessingException {
        String code = requestBody.get("code");
        String accessToken = getGoogleToken(code);
        Map<String, Object> userInfo = getGoogleUserInfo(accessToken);
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        Log.info(email + " : " + name);
        User googleUser = registerUserIfNeeded(email, name);
        return jwtUtil.createToken(googleUser.getId(), googleUser.getRole());
    }

    private Map<String, Object> getGoogleUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Map.class);
        return response.getBody();
    }

    private String getGoogleToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientPassword);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GOOGLE_TOKEN_URL, request, Map.class);
        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token");
        }
        return (String) body.get("access_token");
    }

    private OauthUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        URI uri = UriComponentsBuilder.fromUriString("https://kapi.kakao.com").path("/v2/user/me").encode().build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri).headers(headers).body(new LinkedMultiValueMap<>());
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties").get("nickname").asText();
        String email = jsonNode.get("kakao_account").get("email").asText();
        Log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new OauthUserInfoDto(id, nickname, email);
    }

    private String getToken(String code, String url, String path, String clientId, String redirectUri) throws JsonProcessingException {
        URI uri = UriComponentsBuilder.fromUriString(url).path(path).encode().build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri).headers(headers).body(body);
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    // if there is no matching email user it will generate a new user,
    // 유저가 없을시에 유서를 새로 생성합니다
    private User registerUserIfNeeded(String email, String name) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return user;
        }
        Log.info("새로운 유저 생성");
        String encodedPassword = passwordEncoder.encode(UUID.randomUUID().toString());
        user = new User(email, encodedPassword, name, UserRoleEnum.USER);
        userRepository.save(user);
        return user;
    }
}
