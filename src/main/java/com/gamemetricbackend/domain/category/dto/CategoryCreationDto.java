package com.gamemetricbackend.domain.category.dto;

import lombok.Getter;

@Getter
public class CategoryCreationDto {
        String categoryName;

        public CategoryCreationDto(String catagoryName) {
                this.categoryName = catagoryName;
        }
}
