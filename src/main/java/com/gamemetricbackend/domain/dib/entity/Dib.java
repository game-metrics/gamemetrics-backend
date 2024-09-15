package com.gamemetricbackend.domain.dib.entity;

import com.gamemetricbackend.domain.user.entitiy.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class Dib {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Long followerId;

    // todo : need to refactor , probably need to make middle table between Streamers and a foller Dib
    @OneToMany(fetch = FetchType.EAGER)
    private List<User> streamer;
}
