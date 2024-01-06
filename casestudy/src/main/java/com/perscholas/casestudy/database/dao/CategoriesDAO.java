package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface CategoriesDAO extends JpaRepository<Categories, Long> {

    public  Categories findById(Integer id);
    public  Categories findByName(String  name);

    @Modifying
    @Transactional
    int deleteByName(String  name);

}
