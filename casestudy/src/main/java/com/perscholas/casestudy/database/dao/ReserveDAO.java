package com.perscholas.casestudy.database.dao;

import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveDAO extends JpaRepository<Reservation, Long> {



   // Optional<Reservation> findByBookIdAndUser_Id(Integer bookId, Integer userId);


   //Reservation findByBookAndUser(Book book, User user);

  // @Query("SELECT r FROM Reservation r WHERE r.user = :user AND r.book = :book")
   // List<Reservation> findByUserAndBook(@Param("user") User user, @Param("book") Book book);




    public  Reservation findById(Integer id);
}
