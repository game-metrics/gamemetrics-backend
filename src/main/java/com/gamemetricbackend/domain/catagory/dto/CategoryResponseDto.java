package com.gamemetricbackend.domain.catagory.dto;

import lombok.Getter;

@Getter
public class CategoryResponseDto {
    // Getters
    private Long id;
    private String catagory;

    public CategoryResponseDto(Long id, String catagory) {
        this.id = id;
        this.catagory = catagory;
    }
}
