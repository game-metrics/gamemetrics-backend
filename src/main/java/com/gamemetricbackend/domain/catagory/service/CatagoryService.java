package com.gamemetricbackend.domain.catagory.service;

import com.esotericsoftware.minlog.Log;
import com.gamemetricbackend.domain.catagory.dto.CategoryCreationDto;
import com.gamemetricbackend.domain.catagory.dto.CategoryResponseDto;
import com.gamemetricbackend.domain.catagory.entity.Category;
import com.gamemetricbackend.domain.catagory.repository.CategoryRepository;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatagoryService {

    private final CategoryRepository catagoryRepository;
    public Boolean create(CategoryCreationDto categoryCreationDto, UserRoleEnum role) {
        if(role.equals(UserRoleEnum.ADMIN)){
            try {
                catagoryRepository.save(new Category(categoryCreationDto.getCategoryName()));
            }catch (Exception e){
                Log.error("the catagory already exists");
                return false;
            }
        }
        else {
            Log.error("there is no permission");
            return false;
        }
        return true;
    }

    public List<CategoryResponseDto> getCategoryList() {
        return catagoryRepository.getAllCatagory();
    }
}
