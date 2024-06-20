package com.gamemetricbackend.global.config;

import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig implements WebMvcConfigurer {

//    private final JwtUtil jwtUtil;
//    private final AuthenticationConfiguration authenticationConfiguration;
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
//        throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, userRepository,
//            passwordEncoder);
//        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
//        return filter;
//    }
//
//    @Bean
//    public JwtAuthorizationFilter jwtAuthorizationFilter() {
//        return new JwtAuthorizationFilter(jwtUtil);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // CSRF 설정
//        http.csrf((csrf) -> csrf.disable());
//
//        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
//        http.sessionManagement((sessionManagement) ->
//            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        http.authorizeHttpRequests((authorizeHttpRequests) ->
//            authorizeHttpRequests
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//                .permitAll() // resources 접근 허용 설정
//                .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
//                .requestMatchers("/users/**").permitAll() // '/api/user/'로 시작하는 요청 모두 접근 허가
//                .requestMatchers(HttpMethod.GET,"/posts").permitAll() // 게시글 전체조회
//                .requestMatchers(HttpMethod.GET,"/posts/{postId}").permitAll() // 게시글 단일 조회
//                .requestMatchers(HttpMethod.GET,"/posts/{postId}/comments").permitAll() // 게시글에 달린 댓글조회
//                .requestMatchers(HttpMethod.POST, "/posts/compensation").permitAll()
//                .requestMatchers(HttpMethod.POST, "/products/update").permitAll()
//                .requestMatchers("/posts/best").permitAll()
//                .requestMatchers("/posts/notice").permitAll()
//                .requestMatchers("/notification/subscribe").permitAll()
//                .requestMatchers("/actuator/**").permitAll()
//                .anyRequest().authenticated() // 그 외 모든 요청 인증처리
//        );
//
//        //필터 관리
//        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        String[] allowedOrigins = {"http://52.79.44.5:3000","http://localhost:3000"};
//        registry.addMapping("/**") // 요청을 받을 엔드포인트를 지정합니다.
//            .allowedOriginPatterns(allowedOrigins) // 허용할 origin을 설정합니다.
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH","OPTIONS") // 허용할 HTTP 메서드를 설정합니다.
//            .allowCredentials(true)
//            .allowedHeaders("Content-Type", "Authorization",
//                "Access-Control-Allow-Origin", "Access-Control-Expose-Headers") // 허용할 헤더를 설정합니다.
//            .exposedHeaders("Authorization");
//    }


}
