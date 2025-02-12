package com.gamemetricbackend.domain.user.entitiy;


import com.gamemetricbackend.domain.user.dto.request.SignupRequestDto;
import com.gamemetricbackend.global.entity.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user", indexes = {@Index(name = "user_index",columnList = "nickname")})
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "nickname",nullable = false ,unique = true)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(SignupRequestDto requestDto) {
        super();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
        this.nickname = requestDto.getNickname();
        this.role = UserRoleEnum.USER;

        if(requestDto.isAdmin()){
            this.role = UserRoleEnum.ADMIN;
        }
    }

    public User(Long id, UserRoleEnum userRoleEnum) {
        this.id = id;
        this.role = userRoleEnum;
    }

    public User(String email, String password,String nickname, UserRoleEnum userRoleEnum) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = userRoleEnum;
    }
    public boolean UpdatePassword(String password){
        this.password = password;
        return true;
    }
}
