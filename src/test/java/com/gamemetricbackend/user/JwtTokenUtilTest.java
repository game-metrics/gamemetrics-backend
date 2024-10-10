package com.gamemetricbackend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.esotericsoftware.minlog.Log;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.global.util.JwtUtil;
import com.gamemetricbackend.global.util.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


public class JwtTokenUtilTest {

    private JwtUtil jwtUtil;
    private RedisUtil redisUtil;
    private String secretKey;

    HttpServletResponse response;

    Long userId;
    UserRoleEnum roleEnum;

    @BeforeEach
    void setUp(){
        // given
        secretKey = "7Iqk7YyM66W07YOA7L2U65Sp7YG065+9U3ByaW5n6rCV7J2Y7Yqc7YSw7LWc7JuQ67mI7J6F64uI64ukLg==";

        jwtUtil = new JwtUtil(redisUtil);
        jwtUtil.TestEnviroment(secretKey);
    }

    @Test
    @DisplayName("Access 토큰 생성 및 정보 가져오기 기능 테스트")
    void createAccessToken(){
        //given
        userId = 1L;
        roleEnum = UserRoleEnum.USER;


        //when
        String accessToken = jwtUtil.createAccessToken(userId,roleEnum).substring(7); // bearer prefix 제거
        boolean validateion = jwtUtil.validateToken(accessToken,response); // 만료기한 validation
        long id = jwtUtil.getUserIdFromToken(accessToken); // 정보 가져오기

        //then
        Log.info(accessToken);
        assertTrue(validateion);
        assertEquals(id,userId);
    }

    @Test
    @DisplayName("Refresh 토큰 생성 및 정보 가져오기 기능 테스트")
    void createRefreshToken() {
        //given
        userId = 1L;
        roleEnum = UserRoleEnum.USER;
        Date date = new Date();

        //when
        String refreshToken = jwtUtil.createRefreshToken(date,userId,roleEnum).substring(7); // bearer prefix 제거
        boolean validateion = jwtUtil.validateToken(refreshToken,response); // 만료기한 validation
        long id = jwtUtil.getUserIdFromToken(refreshToken); // 정보 가져오기

        //then
        Log.info(refreshToken);
        assertEquals(id,userId);
        assertTrue(validateion);
    }

    @Test
    @DisplayName("만료된 JWT 검증 및 처리 테스트")
    void validateExpiredTokens() {
        //given
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VySWQiLCJ1c2VySWQiOjEsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzI4MDQwNDA1LCJpYXQiOjE3Mjc5NTQwMDV9.OS5G85Py5Fp1gaKSZw0OJJW7SVdnnSmjpz-m_nyMnMc";

        //when
        boolean validation = jwtUtil.validateToken(expiredToken,response);

        //then
        assertFalse(validation); // expired means false
    }
}
