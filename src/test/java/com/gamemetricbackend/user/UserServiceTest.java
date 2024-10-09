package com.gamemetricbackend.user;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.domain.user.service.UserServiceImpl;
import com.gamemetricbackend.global.exception.NoSuchUserException;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository mockRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @DisplayName("generate user")
    void createUser() {
        //given
        boolean hasError = false;
        SignupRequestDto userdto = new SignupRequestDto();
        userdto.setUsername("testuser");
        userdto.setPassword("testpassword");
        User user = new User(userdto);

        userService = new UserServiceImpl(mockRepo,passwordEncoder);

        //when
        // since the signup method returns void, it only requires to check if it throws exceptions
        HttpServletResponse response = null;
        try{
            userService.signUp(userdto);
        }catch (Exception e){
            hasError = true;
        }
        //then
        assertFalse(hasError);
    }

    @Test
    @DisplayName("update password")
    void login() throws NoSuchUserException {
        //given
        boolean result = false;
        long id = 1L;
        String currentPassword = "oldpass";
        String newPassword = "newpass";
        User user= new User();
        UserServiceImpl userService = new UserServiceImpl(mockRepo,passwordEncoder);
        UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto();
        requestDto.setCurrentPassword(currentPassword);
        requestDto.setNewPassword(newPassword);

        //when
        given(mockRepo.findPasswordById(id)).willReturn(Optional.of(user));
        given(!passwordEncoder.matches(currentPassword, user.getPassword())).willReturn(true);
        result = userService.UpdatePassword(id,requestDto);

        //then
        assertTrue(result);
    }

}
