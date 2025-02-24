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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<ResponseDto<Boolean>> createCategory(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        ,@RequestBody CategoryCreationDto catagoryCreationDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(
            categoryService.create(catagoryCreationDto,userDetails.getRole())));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<CategoryResponseDto>>> getCategoryList(){
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success(
            categoryService.getCategoryList()));
    }
}
