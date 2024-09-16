package com.gamemetricbackend.domain.dib.repository;

import com.gamemetricbackend.domain.dib.entity.Dib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DibRepository extends JpaRepository<Dib, Long>,DibsRepositoryQuery {


}
