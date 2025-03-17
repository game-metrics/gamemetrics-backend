package com.gamemetricbackend.domain.category.controller;

import com.gamemetricbackend.domain.category.dto.CategoryCreationDto;
import com.gamemetricbackend.domain.category.dto.CategoryResponseDto;
import com.gamemetricbackend.domain.category.service.CategoryService;
import com.gamemetricbackend.global.aop.dto.ResponseDto;
import com.gamemetricbackend.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 카테고리 관련 HTTP 요청을 처리하는 컨트롤러
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    // 카테고리 비즈니스 로직을 처리하는 서비스 의존성 주입
    private final CategoryService categoryService;

    /**
     * 카테고리 생성 API
     * POST /category
     *
     * @param userDetails 로그인한 사용자 정보 (SecurityContext에서 주입됨)
     * @param categoryCreationDto 클라이언트에서 전달한 카테고리 생성 요청 데이터
     * @return 카테고리 생성 성공 여부(Boolean)를 담은 ResponseDto
     */
    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> createCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CategoryCreationDto categoryCreationDto) {

        // 카테고리 생성 후 성공 여부 반환 (201 Created)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDto.success(
                        categoryService.create(categoryCreationDto, userDetails.getRole())));
    }

    /**
     * 카테고리 목록 조회 API
     * GET /category
     *
     * @return 카테고리 리스트를 담은 ResponseDto
     */
    @GetMapping
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> getCategoryList() {

        // 카테고리 목록 조회 후 반환 (200 OK)
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.success(categoryService.getCategoryList()));
    }
}
