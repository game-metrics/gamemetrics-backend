package com.gamemetricbackend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.esotericsoftware.minlog.Log;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.global.util.JwtUtil;
import com.gamemetricbackend.global.util.RedisUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
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
    void createAccessToken(){
        //given
        userId = 1L;
        roleEnum = UserRoleEnum.USER;


        //when
        String accessToken = jwtUtil.createAccessToken(userId,roleEnum).substring(7); //remove bearer prefix
        boolean validateion = jwtUtil.validateToken(accessToken,response); // expiration validation
        long id = jwtUtil.getUserIdFromToken(accessToken);

        //then
        Log.info(accessToken);
        assertTrue(validateion);
        assertEquals(id,userId);
    }

    @Test
    void createRefreshToken() {
        //given
        userId = 1L;
        roleEnum = UserRoleEnum.USER;
        Date date = new Date();

        //when
        String refreshToken = jwtUtil.createRefreshToken(date,userId,roleEnum).substring(7); //remove bearer prefix
        boolean validateion = jwtUtil.validateToken(refreshToken,response); // expiration validation
        long id = jwtUtil.getUserIdFromToken(refreshToken);

        //then
        Log.info(refreshToken);
        assertEquals(id,userId);
        assertTrue(validateion);
    }

    @Test
    void validateExpiredTokens() {
        //given
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VySWQiLCJ1c2VySWQiOjEsImF1dGgiOiJVU0VSIiwiZXhwIjoxNzI4MDQwNDA1LCJpYXQiOjE3Mjc5NTQwMDV9.OS5G85Py5Fp1gaKSZw0OJJW7SVdnnSmjpz-m_nyMnMc";

        //when
        boolean validation = jwtUtil.validateToken(expiredToken,response);

        //then
        assertFalse(validation); // expired means false
    }
}
