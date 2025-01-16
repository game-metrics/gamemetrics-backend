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
import lombok.RequiredArgsConstructor;
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
public class OAuthServiceImpl implements OAuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    //Kakao
    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.redirect.uri}")
    private String redirectUri;

    public OAuthServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder, RestTemplateBuilder restTemplateBuilder , JwtUtil jwtUtil){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplateBuilder.build();
    }
    public String KakaoAuth(Map<String, String> requestBody) throws JsonProcessingException {

        String code = requestBody.get("code");
        String url = "https://kauth.kakao.com";
        String path = "/oauth/token";

        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code,url, path,clientId,redirectUri);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        OauthUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerUserIfNeeded(kakaoUserInfo.getEmail(),kakaoUserInfo.getNickname());

        // 4. Jwt 토큰 반환
        return jwtUtil.createToken(kakaoUser.getId(), kakaoUser.getRole());
    }

    @Override
    public String GoogleAuth(Map<String, String> requestBody) throws JsonProcessingException {
        String code = requestBody.get("code");

        // 1. request Authentication token using the given code "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getGoogleToken(code);

        // 2. Using token to host Google API to get users information, 토큰으로 google API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        Map<String, Object> userInfo = getGoogleUserinfo(accessToken);

        // 3. Extracting email and password 이메일 하고 닉 가져오기.
        String email = (String) userInfo.get("email");
        String name = (String) userInfo.get("name");
        Log.info(email +" : "+ name);

        // Register if needed 필요시에 회원가입
        User googleUser = registerUserIfNeeded(email,name);

        // 4. Jwt 토큰 반환
        return jwtUtil.createToken(googleUser.getId(), googleUser.getRole());
    }

    private Map<String, Object> getGoogleUserinfo(String accessToken) {
        // TODO : Change the hard coding 하드코딩 제거
        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(accessToken);

        HttpEntity<String> userInfoRequest = new HttpEntity<>(authHeaders);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUrl, HttpMethod.GET, userInfoRequest, Map.class);

        Map<String, Object> userInfo = userInfoResponse.getBody();
        return userInfo;
    }

    private String getGoogleToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // TODO :fix the hard coding and import from app.properties 하드 코딩 제거
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", "175362941207-5utd0bap67slhe4o8511qjcacetb92fe.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("redirect_uri", "http://localhost:3000/sign-in/google");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        String tokenUrl = "https://oauth2.googleapis.com/token";

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, tokenRequest, Map.class);
        Map<String, Object> tokenBody = tokenResponse.getBody();

        if (tokenBody == null || !tokenBody.containsKey("access_token")) {
            throw new IllegalStateException("Failed to retrieve access token");
        }

        return  (String) tokenBody.get("access_token");
    }

    private String getToken(String code , String url , String path , String clientId ,String redirectUri) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
            .fromUriString(url)
            .path(path)
            .encode()
            .build()
            .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
            .post(uri)
            .headers(headers)
            .body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            requestEntity,
            String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private OauthUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
            .fromUriString("https://kapi.kakao.com")
            .encode()
            .path("/v2/user/me")
            .build()
            .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
            .post(uri)
            .headers(headers)
            .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
            requestEntity,
            String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
            .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
            .get("email").asText();

        Log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new OauthUserInfoDto(id, nickname, email);
    }

    private User registerUserIfNeeded(String email,String name) {
        // DB 에 중복된 Kakao 이메일 이 있는지 확인
        User user = userRepository.findByEmail(email).orElse(null);

        // 회원이 이미 있을경우
        if (user != null) {
            return user;
        }

        Log.info("새로운 유저 생성");
        // 신규 회원가입
        String encodedPassword = passwordEncoder.encode(UUID.randomUUID().toString());

        user = new User(email, encodedPassword, name, UserRoleEnum.USER);
        userRepository.save(user);

        return user;
    }
}
