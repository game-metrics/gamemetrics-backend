package com.gamemetricbackend.domain.catagory.dto;

import lombok.Getter;

@Getter
public class CategoryCreationDto {
        String categoryName;

        public CategoryCreationDto(String catagoryName) {
                this.categoryName = catagoryName;
        }
}
