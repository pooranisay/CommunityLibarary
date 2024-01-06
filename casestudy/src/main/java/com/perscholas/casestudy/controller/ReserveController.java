package com.perscholas.casestudy.controller;

import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.dao.ReserveDAO;
import com.perscholas.casestudy.database.dao.UserRoleDAO;
import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.formbean.CreateBookFormBean;
import com.perscholas.casestudy.formbean.ReserveBookFormBean;
import com.perscholas.casestudy.service.ReservationService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import com.perscholas.casestudy.Security.AuthenticatedUserService;
import com.perscholas.casestudy.database.dao.UserDAO;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.RegisteredUserFormBean;
import com.perscholas.casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
public class ReserveController {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private ReserveDAO reserveDAO;

    @Autowired
    private ReservationService reservationService;

    private int maximumWeeksToExtend = 3;
    private int initialTimeExtended = 0;

    @PostMapping("/book/reserve/{id}")

    public ModelAndView ReserveBook(@PathVariable int id, @RequestParam(required = false) String success) {
        log.info("######################### In Reserve #########################");
        ModelAndView response = new ModelAndView("book/reserve");

        Book book = bookDAO.findById(id);
        // Integer userId= userRoleDAO.
        // User user=;
        User user = authenticatedUserService.loadCurrentUser();
        //List<Reservation> existingReservation = reserveDAO.findByBookAndUserAndStatus(book, user, "Reserved");
        //List<Reservation> NoReservation = reserveDAO.findByBookAndUserAndStatus(book, user, " ");
        // Create a reservation with current date and due date as current date plus 20 days
        Reservation reservation = new Reservation();


        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setCheckoutDate(LocalDate.now());
        reservation.setDueDate(LocalDate.now().plusDays(20));
        reservation.setStatus("Reserved-in progress");
        reserveDAO.save(reservation);
        log.info("######################### In Reserve setting success #########################");

        //reservationService.saveReservation(reservation);

        // Add the book and reservation information to the ModelAndView if needed
        response.addObject("book", book);
        response.addObject("user", user);
        response.addObject("reservation", reservation);

        // Add a success message
        if ("true".equals(success)) {
            response.addObject("successMessage", "Reservation successful!");
        } else {
            log.warn("Book or user not found for reservation. Book ID: {}, User ID: {}", id, user != null ? user.getId() : "null");
            // You might want to handle this case, for example, redirect to an error page
        }

        return response;
    }

    @GetMapping("/book/myReserve")
    // Retrieve reservation details based on the reservationId
    public ModelAndView MyReserve(@RequestParam(required = false) String search) {
        log.info("######################### In Reserve Submit #########################");
        ModelAndView response = new ModelAndView("book/myReserve");
        //Book book = bookDAO.findById(id);
        User user = authenticatedUserService.loadCurrentUser();
        List<Book> processedBookReservations = bookDAO.getProcessedBookReservations(user);
        response.addObject("bookVar", processedBookReservations);
        return response;
    }

    @GetMapping("/book/extend")
    public ModelAndView extendRequest(
            @RequestParam Integer bookId, @RequestParam Integer reservationId,
            @RequestParam(required = false, defaultValue = "1") int weeksToExtend, HttpServletRequest request) {
        log.info("######################### In Book Extend #########################");
        ModelAndView response = new ModelAndView("book/myReserve");
        Book book = bookDAO.findById(bookId);
        Reservation reservation = reserveDAO.findById(reservationId);
        int count = initialTimeExtended + 1;
        int extensionsLeft = maximumWeeksToExtend - initialTimeExtended;
        reservation.setDueDate(reservation.getDueDate());
        // Perform the extension
        if (reservation.getDueDate() != null
                && !reservation.getDueDate().isAfter(reservation.getCheckoutDate().plusDays(30))) {
            {
                reservation.setDueDate(reservation.getDueDate().plusDays(7));
                reserveDAO.save(reservation);
                response.addObject("successMessage", "Extension successful!");
                log.debug("duedate=" + reservation.getDueDate());

            } }
            else{

                log.warn("we cannot extend the book  for reservationId: " + reservationId);
                response.addObject("errorMessage", book.getName() + " book - Extensions are not allowed for more than two weeks.");

            }

            User user = authenticatedUserService.loadCurrentUser();
            List<Book> processedBookReservations = bookDAO.getProcessedBookReservations(user);
            response.addObject("bookVar", processedBookReservations);

            return response;

        }


   @GetMapping("/book/unReserve")
    public ModelAndView unReserveRequest(
           @RequestParam Integer bookId, @RequestParam Integer reservationId,
           @RequestParam(required = false, defaultValue = "1") int weeksToExtend, HttpServletRequest request)
   {
       log.info("######################### In Book Extend #########################");
       ModelAndView response = new ModelAndView("book/myReserve");
       Book book=bookDAO.findById(bookId);
       Reservation reservation = reserveDAO.findById(reservationId);
       reserveDAO.delete(reservation);
       response.addObject("successMessage", "Book returned  successful!");
       User user = authenticatedUserService.loadCurrentUser();
       List<Book> processedBookReservations = bookDAO.getProcessedBookReservations(user);
       response.addObject("bookVar", processedBookReservations);

       return response;
   }




    @GetMapping("/book/confirmReserve")
    public ModelAndView confirmReserve(
            @RequestParam Integer bookId, @RequestParam Integer reservationId,
            HttpServletRequest request)
    {
        log.info("######################### In ConfirmReserve#########################");
        ModelAndView response = new ModelAndView("book/reserveProcess");
        Book book=bookDAO.findById(bookId);
        Reservation reservation = reserveDAO.findById(reservationId);
        reservation.setStatus("Reserved");
        reserveDAO.save(reservation);
        response.addObject("successMessage", "Reservation confirmed successful!");
        User user = authenticatedUserService.loadCurrentUser();
        //reservation.setStatus("Available");
        List<Book>confirmReserve=bookDAO.getConfirmProcessedBookReservations();
        response.addObject("bookVar", confirmReserve);

        return response;
    }




}
