package com.gamemetricbackend.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.global.entity.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Log4j2(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final long ACCESS_TOKEN_TIME =  24 * 60 * 60 * 1000L; // 하루
    private final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일
    public static final String USER_ID = "userId";

    private final RedisUtil redisUtil;

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(Long userId, UserRoleEnum roleEnum) throws JsonProcessingException {
        Date date = new Date();

        String redisKeys = "UserID : " + userId;

        if (redisUtil.hasKey(redisKeys)) {
            return UpdateAccessTokens(userId,roleEnum,redisKeys);
        } else {
            log.info("새로운 refresh 토큰 생성");
            return SaveNewRefreshToken(date, userId, roleEnum, redisKeys);
        }
    }

    public String UpdateAccessTokens(Long userId,UserRoleEnum roleEnum, String redisKeys)
        throws JsonProcessingException {

        RefreshToken refreshToken = redisUtil.get(redisKeys);

        String accessToken = createAccessToken(userId, roleEnum);
        log.info("새로운 access 토큰 발급");
        refreshToken.update(accessToken);

        redisUtil.set(redisKeys, refreshToken, (int) REFRESH_TOKEN_TIME);

        return accessToken;
    }

    public String SaveNewRefreshToken(Date date, Long userId, UserRoleEnum roleEnum,
        String redisKeys) throws JsonProcessingException {

        String accessToken = createAccessToken(userId, roleEnum);

        String refreshToken = createRefreshToken(date,userId,roleEnum);

        RefreshToken token = RefreshToken
            .builder()
            .refreshToken(refreshToken)
            .previousAccessToken(accessToken.substring(7))
            .userId(userId)
            .build();

        redisUtil.set(redisKeys, token, (int) REFRESH_TOKEN_TIME);

        return accessToken;
    }

    public String createRefreshToken(Date date, Long userId, UserRoleEnum roleEnum) {
        return BEARER_PREFIX +
            Jwts.builder()
                .claim("userId", userId)
                .claim("role", roleEnum)
                .setIssuedAt(new Date(date.getTime())) // 토큰 발행 시간 정보
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME)) // set Expire Time
                .signWith(key, signatureAlgorithm)  // 사용할 암호화 알고리즘과
                .compact();// signature 에 들어갈 secret 값 세팅
    }

    public String createAccessToken(Long userId, UserRoleEnum roleEnum) {
        Date date = new Date();
        return BEARER_PREFIX +
            Jwts.builder()
                .claim("userId", userId)
                .claim("role", roleEnum)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getJwtFromHeader(
        final HttpServletRequest request
    ) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Claims claimUserInfoFromToken(
        final String token
    ) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            .getBody();
    }

    public Claims getMemberInfoFromExpiredToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token,HttpServletResponse res) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            return validateRefreshToken(token,res);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String token, HttpServletResponse response){
        log.info("Refresh 토큰 검증 후 새로운 access 토큰 발급 시도");
        try{
            Claims info = getMemberInfoFromExpiredToken(token);
            Long userId = ((Number) info.get(USER_ID)).longValue();

            UserRoleEnum role = UserRoleEnum.USER;
            String redisKeys = "UserID : " + userId;

            RefreshToken refreshToken = redisUtil.get(redisKeys);

            // refresh token prefix removal
            String refreshTokenValue = refreshToken.getRefreshToken().substring(7);

            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshTokenValue);

            String newToken = createToken(userId, role);

            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, newToken);
            ObjectNode json = new ObjectMapper().createObjectNode();
            String newResponse = new ObjectMapper().writeValueAsString(json);
            response.setContentType("application/json");
            response.setContentLength(newResponse.length());
            response.getOutputStream().write(newResponse.getBytes());

        }catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Refresh 토큰 도 말료 되었습니다");
        } catch (JsonProcessingException e) {
            log.error("Redis 오브젝트 변환 에러");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            log.error("Redis 연결오류 혹은 해당값이 없습니다");
        }
        return false;
    }

    public boolean TestEnviroment(String secretKey) {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        this.secretKey = secretKey;
        this.signatureAlgorithm = SignatureAlgorithm.HS256;
        this.key = Keys.hmacShaKeyFor(bytes);
        return true;
    }

    public Long getUserIdFromToken(String token) {
        Claims info = getMemberInfoFromExpiredToken(token);
        return ((Number) info.get("userId")).longValue();
    }
}
