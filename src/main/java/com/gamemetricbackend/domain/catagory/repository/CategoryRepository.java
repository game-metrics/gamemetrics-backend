package com.gamemetricbackend.domain.catagory.repository;

import com.gamemetricbackend.domain.catagory.dto.CategoryResponseDto;
import java.util.List;

import com.gamemetricbackend.domain.catagory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT new com.gamemetricbackend.domain.catagory.dto.CategoryResponseDto(c.id, c.category) FROM Category c")
    List<CategoryResponseDto> getAllCatagory();
}
