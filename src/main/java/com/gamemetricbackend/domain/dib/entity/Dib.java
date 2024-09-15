package com.gamemetricbackend.domain.dib.entity;

import com.gamemetricbackend.domain.user.entitiy.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
public class Dib {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private Long followerId;

    @Column(nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private User streamer;

}
