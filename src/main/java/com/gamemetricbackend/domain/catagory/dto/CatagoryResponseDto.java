package com.gamemetricbackend.domain.catagory.dto;

import lombok.Getter;

@Getter
public class CatagoryResponseDto {
    // Getters
    private Long id;
    private String catagory;

    public CatagoryResponseDto(Long id, String catagory) {
        this.id = id;
        this.catagory = catagory;
    }
}
