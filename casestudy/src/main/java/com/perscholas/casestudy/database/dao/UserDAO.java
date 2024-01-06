package com.perscholas.casestudy.database.dao;


import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserDAO extends JpaRepository<User, Long> {
    public User findByEmailIgnoreCase(String email);

    @Modifying
    @Transactional
    int deleteByEmailIgnoreCase(String firstName);

    //public User findById(Integer id);
}
