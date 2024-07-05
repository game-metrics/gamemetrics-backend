package com.gamemetricbackend.domain.user.entitiy;


import com.gamemetricbackend.domain.user.dto.SignupRequestDto;
import com.gamemetricbackend.global.entity.TimeStamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public User(SignupRequestDto requestDto) {
        super();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        if(requestDto.isAdmin()){
            this.role = UserRoleEnum.ADMIN;
        }
        else {
            this.role = UserRoleEnum.USER;
        }
    }

    public User(Long id, UserRoleEnum userRoleEnum) {
        this.id = id;
        this.role = userRoleEnum;
    }

    public User(String username, String password, UserRoleEnum userRoleEnum) {
        this.username = username;
        this.password = password;
        this.role = userRoleEnum;
    }
}
