package com.gamemetricbackend.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.domain.user.dto.request.UpdatePasswordRequestDto;
import com.gamemetricbackend.domain.user.dto.temporal.SignUpResponseDto;
import com.gamemetricbackend.domain.user.entitiy.User;
import com.gamemetricbackend.domain.user.repository.UserRepository;
import com.gamemetricbackend.domain.user.service.UserServiceImpl;
import com.gamemetricbackend.global.exception.NoSuchUserException;
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
        SignupRequestDto userdto = new SignupRequestDto();
        userdto.setEmail("testuser@naver.com");
        userdto.setPassword("testpassword");
        User user = new User(userdto);

        userService = new UserServiceImpl(mockRepo,passwordEncoder);
        SignupRequestDto requestDto = new SignupRequestDto();


        //when
        given(mockRepo.save(any())).willReturn(user);
        SignUpResponseDto responseDto = userService.signUp(requestDto);

        //then
        assertEquals(responseDto.getUsername(),user.getEmail());
    }

    @Test
    @DisplayName("update password")
    void login() throws NoSuchUserException {
        //given
        boolean result;
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
