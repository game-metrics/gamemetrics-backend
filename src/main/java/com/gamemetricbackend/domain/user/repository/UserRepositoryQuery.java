package com.gamemetricbackend.domain.user.repository;

import com.gamemetricbackend.domain.user.dto.response.UserInfoResponseDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 사용자 데이터 쿼리를 위한 리포지토리 인터페이스입니다.
 * 다양한 조건으로 사용자를 찾고, 페이징 처리를 지원하는 메서드를 제공합니다.
 */
public interface UserRepositoryQuery {

    /**
     * 사용자 ID로 사용자의 비밀번호를 찾습니다.
     *
     * @param id 사용자 ID
     * @return 사용자가 존재하면 사용자 정보가 포함된 Optional, 존재하지 않으면 빈 Optional
     */
    Optional<User> findPasswordById(Long id);

    /**
     * 이메일 주소로 사용자를 찾습니다.
     *
     * @param getEmail 사용자의 이메일 주소
     * @return 사용자가 존재하면 사용자 정보가 포함된 Optional, 존재하지 않으면 빈 Optional
     */
    Optional<User> findByEmail(String getEmail);

    /**
     * 사용자의 닉네임으로 사용자를 검색하며, 페이징 기능을 지원합니다.
     *
     * @param name 사용자의 닉네임
     * @param pageable 페이징 정보 (페이지 번호, 페이지 크기, 정렬)
     * @return 사용자 정보가 포함된 UserInfoResponseDto의 페이징된 목록
     */
    Page<UserInfoResponseDto> SearchUsersByNickName(String name, Pageable pageable);
}
