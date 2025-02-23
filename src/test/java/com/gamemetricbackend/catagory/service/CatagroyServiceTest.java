package com.gamemetricbackend.catagory.service;

import com.gamemetricbackend.domain.catagory.dto.CatagoryCreationDto;
import com.gamemetricbackend.domain.catagory.dto.CatagoryResponseDto;
import com.gamemetricbackend.domain.catagory.entity.Catagory;
import com.gamemetricbackend.domain.catagory.repository.CatagoryRepository;
import com.gamemetricbackend.domain.catagory.service.CatagoryService;
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

class CatagoryServiceTest {

    @Mock
    private CatagoryRepository catagoryRepository;

    @InjectMocks
    private CatagoryService catagoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_Catagory_Successful_AdminRole() {
        // Arrange
        CatagoryCreationDto catagoryCreationDto = new CatagoryCreationDto("New Category");
        UserRoleEnum role = UserRoleEnum.ADMIN;

        // Act
        boolean result = catagoryService.create(catagoryCreationDto, role);

        // Assert
        assertTrue(result);
        verify(catagoryRepository, times(1)).save(any(Catagory.class));  // Verifying save was called
    }

    @Test
    void create_Catagory_Failure_AlreadyExists() {
        // Arrange
        CatagoryCreationDto catagoryCreationDto = new CatagoryCreationDto("Existing Category");
        UserRoleEnum role = UserRoleEnum.ADMIN;
        doThrow(new RuntimeException("Category already exists")).when(catagoryRepository).save(any(Catagory.class));

        // Act
        boolean result = catagoryService.create(catagoryCreationDto, role);

        // Assert
        assertFalse(result);
        verify(catagoryRepository, times(1)).save(any(Catagory.class));
    }

    @Test
    void create_Catagory_Failure_NoPermission() {
        // Arrange
        CatagoryCreationDto catagoryCreationDto = new CatagoryCreationDto("New Category");
        UserRoleEnum role = UserRoleEnum.USER;  // Non-admin user

        // Act
        boolean result = catagoryService.create(catagoryCreationDto, role);

        // Assert
        assertFalse(result);
        verify(catagoryRepository, times(0)).save(any(Catagory.class));  // Verifying save was not called
    }

    @Test
    void getCatagoryList_ReturnsCatagoryList() {
        // Arrange
        CatagoryResponseDto catagoryResponseDto1 = new CatagoryResponseDto(10L,"Category1");
        CatagoryResponseDto catagoryResponseDto2 = new CatagoryResponseDto(11L,"Category2");
        List<CatagoryResponseDto> expectedList = Arrays.asList(catagoryResponseDto1, catagoryResponseDto2);

        when(catagoryRepository.getAllCatagory()).thenReturn(expectedList);

        // Act
        List<CatagoryResponseDto> result = catagoryService.getCatagoryList();

        // Assert
        assertEquals(expectedList, result);
    }
}
