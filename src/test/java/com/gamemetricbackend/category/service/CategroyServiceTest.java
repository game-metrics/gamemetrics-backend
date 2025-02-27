package com.gamemetricbackend.category.service;

import com.gamemetricbackend.domain.category.dto.CategoryCreationDto;
import com.gamemetricbackend.domain.category.dto.CategoryResponseDto;
import com.gamemetricbackend.domain.category.entity.Category;
import com.gamemetricbackend.domain.category.repository.CategoryRepository;
import com.gamemetricbackend.domain.category.service.CategoryService;
import com.gamemetricbackend.domain.user.entitiy.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_Catagory_Successful_AdminRole() {
        // Arrange
        CategoryCreationDto catagoryCreationDto = new CategoryCreationDto("New Category");
        UserRoleEnum role = UserRoleEnum.ADMIN;

        // Act
        boolean result = categoryService.create(catagoryCreationDto, role);

        // Assert
        assertTrue(result);
        verify(categoryRepository, times(1)).save(any(Category.class));  // Verifying save was called
    }

    @Test
    void create_Catagory_Failure_AlreadyExists() {
        // Arrange
        CategoryCreationDto catagoryCreationDto = new CategoryCreationDto("Existing Category");
        UserRoleEnum role = UserRoleEnum.ADMIN;
        doThrow(new RuntimeException("Category already exists")).when(categoryRepository).save(any(Category.class));

        // Act
        boolean result = categoryService.create(catagoryCreationDto, role);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void create_Catagory_Failure_NoPermission() {
        // Arrange
        CategoryCreationDto catagoryCreationDto = new CategoryCreationDto("New Category");
        UserRoleEnum role = UserRoleEnum.USER;  // Non-admin user

        // Act
        boolean result = categoryService.create(catagoryCreationDto, role);

        // Assert
        assertFalse(result);
        verify(categoryRepository, times(0)).save(any(Category.class));  // Verifying save was not called
    }

    @Test
    void getCatagoryList_ReturnsCatagoryList() {
        // Arrange
        CategoryResponseDto catagoryResponseDto1 = new CategoryResponseDto(10L,"Category1");
        CategoryResponseDto catagoryResponseDto2 = new CategoryResponseDto(11L,"Category2");
        List<CategoryResponseDto> expectedList = Arrays.asList(catagoryResponseDto1, catagoryResponseDto2);

        when(categoryRepository.getAllCategory()).thenReturn(expectedList);

        // Act
        List<CategoryResponseDto> result = categoryService.getCategoryList();

        // Assert
        assertEquals(expectedList, result);
    }
}
