package com.gamemetricbackend.domain.catagory.dto;

import lombok.Getter;

@Getter
public class CatagoryCreationDto {
        String catagoryName;

        public CatagoryCreationDto(String catagoryName) {
                this.catagoryName = catagoryName;
        }
}
