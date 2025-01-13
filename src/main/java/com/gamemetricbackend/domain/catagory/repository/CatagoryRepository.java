package com.gamemetricbackend.domain.catagory.repository;

import com.gamemetricbackend.domain.catagory.dto.CatagoryResponseDto;
import com.gamemetricbackend.domain.catagory.entity.Catagory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CatagoryRepository extends JpaRepository<Catagory, Long> {

    @Query("SELECT new com.gamemetricbackend.domain.catagory.dto.CatagoryResponseDto(c.id, c.catagory) FROM Catagory c")
    List<CatagoryResponseDto> getAllCatagory();
}
