package com.perscholas.casestudy.service;

import com.perscholas.casestudy.Security.AuthenticatedUserService;
import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.dao.ReserveDAO;
import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.ReserveBookFormBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perscholas.casestudy.database.dao.UserDAO;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    private ReserveDAO reserveDAO;

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private UserDAO userDAO; // Assuming you have a UserDAO

    @Autowired
    private BookDAO bookDAO;

    public Book reserveBook(ReserveBookFormBean form) {
        // Assuming form contains necessary information like bookId, userId, etc.
        Integer bookId = form.getBookId();
        Integer userId = form.getUserId();


        // You might need to fetch the Book entity based on the bookId
        Book book = bookDAO.findById(bookId);

        if (book != null) {
            // Set checkout date and due date based on your requirements
            LocalDate checkoutDate = LocalDate.now();
            LocalDate dueDate = checkoutDate.plusDays(20);

            // Create a reservation for the book
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setCheckoutDate(checkoutDate);
            reservation.setDueDate(dueDate);


            // Save the reservation
            reserveDAO.save(reservation);

            // You can perform additional logic if needed

            return book; // Return the reserved book
        } else {
            // Handle the case where the book is not found
            log.warn("Book not found for bookId: {}", bookId);
            return null; // or throw an exception, return a specific response, etc.
        }
    }


     // Assuming you have a ReserveDAO (JpaRepository)

    public void saveReservation(Reservation reservation) {
        // Perform any additional business logic or validation before saving

        // Set the checkout date if it's not already set
        if (reservation.getCheckoutDate() == null) {
            reservation.setCheckoutDate(LocalDate.now());
        }

        // Set the due date if it's not already set
        if (reservation.getDueDate() == null) {
            reservation.setDueDate(LocalDate.now().plusDays(20));
        }

        // Save the reservation to the database
        reserveDAO.save(reservation);
    }




}
