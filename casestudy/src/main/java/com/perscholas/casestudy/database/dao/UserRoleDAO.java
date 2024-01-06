package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

    public List<UserRole> findByUserId(Integer userId);

    @Modifying
    @Transactional
    int deleteByUserId(Integer userId);
}