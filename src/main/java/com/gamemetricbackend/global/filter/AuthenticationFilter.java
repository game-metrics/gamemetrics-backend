package com.gamemetricbackend.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gamemetricbackend.domain.user.dto.LoginRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import com.gamemetricbackend.global.security.CustomAuthenticationToken;
import com.gamemetricbackend.global.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Log4j2(topic = "AuthenticationFilter")
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository,
        PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        setFilterProcessesUrl("/users/login");
    }
    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);

            User user = userRepository.findByUsername(
                    requestDto.getUsername())
                .orElseThrow(
                    () -> new BadCredentialsException("잘못된 아이디를 입력하셨습니다."));
            if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("잘못된 비밀번호를 입력하셨습니다.");
            }
            return new CustomAuthenticationToken(
                user,
                requestDto.getPassword()
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공");
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();
        Long id = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getId();

        String token = jwtUtil.createToken(id, role);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
        ObjectNode json = new ObjectMapper().createObjectNode();
        String newResponse = new ObjectMapper().writeValueAsString(json);
        response.setContentType("application/json");
        response.setContentLength(newResponse.length());
        response.getOutputStream().write(newResponse.getBytes());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("로그인 실패");
        response.setStatus(401);
    }
}
