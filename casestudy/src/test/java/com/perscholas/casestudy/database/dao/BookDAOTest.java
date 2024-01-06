package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookDAOTest {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoriesDAO categoriesDAO;

    @Test
    @Order(1)
    public void createBookTest() {
        // given
        Categories category = new Categories();
        category.setName("Science Fiction");
        category.setDescription("Books related to Science Fiction");
        categoriesDAO.save(category);

        Book book = new Book();
        book.setName("The Science Book");
        book.setImageUrl("science_book.jpg");
        book.setAuthor("John Doe");
        book.setCategory(category);

        // when
        book = bookDAO.save(book);

        // then
        Assertions.assertNotNull(book.getId());
        Assertions.assertEquals("The Science Book", book.getName());
        Assertions.assertEquals("science_book.jpg", book.getImageUrl());
        Assertions.assertEquals("John Doe", book.getAuthor());
        Assertions.assertEquals(category.getId(), book.getCategory().getId());
        // Add assertions for other properties
    }

    @Test
    @Order(2)
    public void updateBookTest() {
        // given
        Book book = bookDAO.findByName("The Science Book");
        Assertions.assertNotNull(book, "Book should not be null for update");

        // when
        book.setImageUrl("new_science_book.jpg");
        bookDAO.save(book);

        // then
        Book updatedBook = bookDAO.findByName("The Science Book");
        Assertions.assertEquals("new_science_book.jpg", updatedBook.getImageUrl());
    }

    @Test
    @Order(3)
    public void findByAuthorTest() {
        // given
        String author = "John Doe";

        // when
        List<Book> booksByAuthor = bookDAO.findByAuthor(author);

        // then
        Assertions.assertFalse(booksByAuthor.isEmpty());
        for (Book book : booksByAuthor) {
            Assertions.assertEquals(author, book.getAuthor());
        }
    }

    @Test
    @Order(4)
    public void deleteBookTest() {
        // given
        Book book = bookDAO.findByName("The Science Book");
        Assertions.assertNotNull(book, "Book should not be null for deletion");

        // Get the associated category
        Categories category = book.getCategory();
        Assertions.assertNotNull(category, "Category should not be null for deletion");

        // when
        bookDAO.deleteByName(book.getName());

        // then
        Book deletedBook = bookDAO.findByName("The Science Book");
        Assertions.assertNull(deletedBook, "Book should be null after deletion");

        // Check if the category is associated with any other books
        List<Book> booksInCategory = bookDAO.findByCategory(category);
        if (booksInCategory.isEmpty()) {
            // If the category is not associated with any other books, delete it
            categoriesDAO.deleteByName(category.getName());
        }

        // Log statements for debugging
        System.out.println("Deleted book: " + deletedBook);
    }


}

