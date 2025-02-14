package com.gamemetricbackend.domain.user.service;

import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.request.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 사용자 관련 기능을 제공하는 서비스 인터페이스.
 */
public interface UserService {

    /**
     * 새로운 사용자를 등록한다.
     *
     * @param requestDto 회원가입 요청 DTO
     * @return 회원가입 결과 DTO
     */
    SignUpResponseDto signUp(SignupRequestDto requestDto);

    /**
     * 사용자의 비밀번호를 변경한다.
     *
     * @param userId 사용자 ID
     * @param updatePasswordRequestDto 비밀번호 변경 요청 DTO
     * @return 비밀번호 변경 성공 여부 (true: 성공, false: 실패)
     * @throws NoSuchUserException 사용자를 찾을 수 없는 경우 발생
     */
    boolean UpdatePassword(Long userId, UpdatePasswordRequestDto updatePasswordRequestDto)
        throws NoSuchUserException;

    /**
     * 특정 사용자의 프로필 정보를 가져온다.
     *
     * @param id 사용자 ID
     * @return 사용자 정보 DTO
     * @throws NoSuchUserException 사용자를 찾을 수 없는 경우 발생
     */
    UserInfoResponseDto getProfile(Long id) throws NoSuchUserException;

    /**
     * 닉네임을 기준으로 사용자를 검색한다.
     *
     * @param name 검색할 사용자 닉네임
     * @param pageable 페이지네이션 정보
     * @return 검색된 사용자 정보 페이지
     */
    Page<UserInfoResponseDto> searchUser(String name, Pageable pageable);
}
