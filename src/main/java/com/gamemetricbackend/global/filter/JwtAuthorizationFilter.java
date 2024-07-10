package com.gamemetricbackend.global.filter;

import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import com.gamemetricbackend.global.security.CustomAuthentication;
import com.gamemetricbackend.global.security.UserDetailsServiceImpl;
import com.gamemetricbackend.global.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Log4j2(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(req);

        // removing bearer prefix
        if (StringUtils.hasText(tokenValue)) {
            if (jwtUtil.validateToken(tokenValue)){
                log.error("Token validation error");
                return;
            }
            log.info(tokenValue);
            Claims info = jwtUtil.claimUserInfoFromToken(tokenValue);
            log.info(info);
            try {
                setAuthentication(info);
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("Token is not readable.");
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    // 인증 처리
    public void setAuthentication(Claims info) {
        log.info("setting Authentication");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(info);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(Claims info) {
        log.info("creating Authentication");
        User user;
        // todo need to refactor, fix why the info.get("auth", ... can not convert to UserRoleEnum.class
        if(info.get("auth",String.class).equals("ADMIN")){
            user = new User(info.get("userId", Long.class), UserRoleEnum.ADMIN);
            //user = new User(info.get("userId", Long.class), info.get("auth",UserRoleEnum.class));
        }
        else {
            user = new User(info.get("userId", Long.class), UserRoleEnum.USER);
        }
        UserDetails userDetails = new UserDetailsImpl(user);
        return new CustomAuthentication(userDetails);
    }
}
