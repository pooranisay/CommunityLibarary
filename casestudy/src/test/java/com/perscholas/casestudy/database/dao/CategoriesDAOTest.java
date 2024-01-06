package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.Categories;
import com.perscholas.casestudy.database.entity.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriesDAOTest {

    @Autowired
    private CategoriesDAO categoriesDAO;

    @Test
    @Order(1)
    public void createCategory() {
        Categories category = new Categories();
        category.setName("Science Fiction");
        category.setDescription("Books related to Science Fiction");

        // When
        categoriesDAO.save(category);

        // Then
        Assertions.assertNotNull(category.getId(), "Category ID should not be null after saving.");
    }

    @Test
    @Order(2)
    public void readCategory() {
        // given
        Categories category = categoriesDAO.findByName("Science Fiction");

        // then
        Assertions.assertNotNull(category, "Category should not be null");
        Assertions.assertEquals("Books related to Science Fiction", category.getDescription());
    }

    @Test
    @Order(3)
    public void updateCategory() {
        // given
        Categories category = categoriesDAO.findByName("Science Fiction");
        Assertions.assertNotNull(category, "Category should not be null");

        // when
        category.setDescription("New description for Science Fiction");
        categoriesDAO.save(category);

        // then
        Categories updatedCategory = categoriesDAO.findByName("Science Fiction");
        Assertions.assertEquals("New description for Science Fiction", updatedCategory.getDescription());
    }

    @Test
    @Order(4)
    public void deleteCategory() {
        // given
        Categories category = categoriesDAO.findByName("Science Fiction");
        Assertions.assertNotNull(category, "Category should not be null");

        // when
        categoriesDAO.deleteByName(category.getName());

        // then
        Categories deletedCategory = categoriesDAO.findByName("Science Fiction");
        Assertions.assertNull(deletedCategory, "Category should be null after deletion");
    }
}