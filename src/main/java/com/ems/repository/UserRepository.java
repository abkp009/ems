package com.ems.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Page<UserEntity> findAll(Pageable page);
}
