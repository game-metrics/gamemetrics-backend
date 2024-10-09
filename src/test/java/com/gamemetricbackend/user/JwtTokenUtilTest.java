package com.gamemetricbackend.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.global.entity.RefreshToken;
import com.gamemetricbackend.global.util.JwtUtil;
import com.gamemetricbackend.global.util.RedisUtil;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

public class JwtTokenUtilTest {

    @Mock
    private static JwtUtil jwtUtil;

    @Mock
    private RedisUtil redisUtil;
    private RefreshToken refreshToken;
    Long userId;
    UserRoleEnum roleEnum;

    @BeforeEach
    void setUp(){
        jwtUtil = new JwtUtil(redisUtil);
        MockitoAnnotations.openMocks(this);
        refreshToken = new RefreshToken();
        userId = 1L;
        roleEnum = UserRoleEnum.USER;
    }

    @Test
    void Login() throws JsonProcessingException {
        Date date = new Date();
        String token =  "bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjc1Miwicm9sZSI6IlVTRVIiLCJleHAiOjE3Mjg1NTYxMTF9.SBXn5xYX39XLjIrW3fudeMvLnn93xN1ntqa7MtMEZsI";

        doNothing().when(redisUtil).set(any(), any(), anyInt());
        when(jwtUtil.createAccessToken(any(),any())).thenReturn(token);

        jwtUtil.createToken(userId,roleEnum);

        System.out.println(refreshToken.getPreviousAccessToken());
    }
}
