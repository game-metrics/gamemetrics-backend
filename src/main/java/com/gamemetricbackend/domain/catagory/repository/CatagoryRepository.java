package com.gamemetricbackend.domain.catagory.repository;

import com.gamemetricbackend.domain.catagory.entity.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatagoryRepository extends JpaRepository<Catagory,Long> {

}
