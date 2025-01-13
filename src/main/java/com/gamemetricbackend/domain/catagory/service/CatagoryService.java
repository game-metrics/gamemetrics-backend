package com.gamemetricbackend.domain.catagory.service;

import com.esotericsoftware.minlog.Log;
import com.gamemetricbackend.domain.catagory.dto.CatagoryCreationDto;
import com.gamemetricbackend.domain.catagory.dto.CatagoryResponseDto;
import com.gamemetricbackend.domain.catagory.entity.Catagory;
import com.gamemetricbackend.domain.catagory.repository.CatagoryRepository;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatagoryService {

    private final CatagoryRepository catagoryRepository;
    public Boolean create(CatagoryCreationDto catagoryCreationDto, UserRoleEnum role) {
        if(role.equals(UserRoleEnum.ADMIN)){
            try {
                catagoryRepository.save(new Catagory(catagoryCreationDto.getCatagoryName()));
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

    public List<CatagoryResponseDto> getCatagoryList() {
        return catagoryRepository.getAllCatagory();
    }
}
