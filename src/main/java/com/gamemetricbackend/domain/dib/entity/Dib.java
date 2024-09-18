package com.gamemetricbackend.domain.dib.entity;

import com.gamemetricbackend.domain.user.entitiy.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Table(indexes = {@Index(name = "dib_index",columnList = "followerId,streamerName")})
public class Dib {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "followerId", nullable = false)
    private Long followerId;

    // todo : need a better way to get streamer name, using less access to the DB and lesser query using dsl.
    @Column( name = "streamerName", nullable = false)
    private String streamerName;
}
