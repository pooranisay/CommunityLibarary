package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import com.perscholas.casestudy.database.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BookDAO extends JpaRepository<Book, Long> {

    public  Book findById(Integer id);
  //  @Query("SELECT c FROM Book c WHERE c.name=:name")
   // List<Book> findByBookName(String name);
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(:name) OR LOWER(b.author) LIKE LOWER(:author) OR b.category.id = :categoryId")
    List<Book> findByBookNameorAuthororCategory(@Param("name") String name,@Param("author")String author,@Param("categoryId") Integer categoryId);

  @Query("SELECT DISTINCT b FROM Book b JOIN b.reservations r WHERE( r.status = 'Reserved' OR r.status = 'Reserved-in progress' ) AND r.user = :user")
  List<Book> getProcessedBookReservations(@Param("user") User user);

  //@Query(value = "SELECT DISTINCT b.*, r.status FROM Book b LEFT JOIN Reservation r ON b.id = r.book_id", nativeQuery = true)
  @Query(value ="SELECT DISTINCT b.*,CASE WHEN r.status IS NULL THEN 'Available' ELSE r.status END AS status FROM Book b LEFT JOIN Reservation r ON b.id = r.book_id" ,nativeQuery = true)
  List<Book> findByBookReservationStatus();

  @Query("SELECT b FROM Book b JOIN Reservation r ON r.book.id = b.id JOIN User u ON r.user.id = u.id WHERE  r.status = 'Reserved-in progress'")
  List<Book>getConfirmProcessedBookReservations();
  // public Book findByCategoryId(Integer categoryId);

    @Query("SELECT b FROM Book b JOIN Reservation r ON r.book.id = b.id JOIN User u ON r.user.id = u.id WHERE  r.status = 'Reserved'")
    List<Book>getManageProcessedBookReservations();

    Book findByName(String name);

    List<Book> findByAuthor(String author);

  @Query("SELECT b FROM Book b WHERE b.category = :category")
  List<Book> findByCategory(@Param("category") Categories category);

    @Modifying
    @Transactional
    int deleteByName(String name);
}
