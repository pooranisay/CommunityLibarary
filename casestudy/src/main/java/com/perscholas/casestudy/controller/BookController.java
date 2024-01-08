package com.perscholas.casestudy.controller;

import com.perscholas.casestudy.Security.AuthenticatedUserService;
import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.dao.ReserveDAO;
import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.CreateBookFormBean;
import com.perscholas.casestudy.formbean.ReserveBookFormBean;
import com.perscholas.casestudy.service.BookService;
import com.perscholas.casestudy.service.ReservationService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller public class BookController {
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookService bookService;

    @Autowired
    private ReserveDAO reserveDAO;

    @Autowired AuthenticatedUserService authenticatedUserService;

    @Autowired
    private ReservationService reservationService;
    @GetMapping("/book/search")
    public ModelAndView search(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer categoryId) {
        ModelAndView response = new ModelAndView("book/search");

        log.debug("in the book search controller method : book name = " + search);
        log.debug("in the book search controller method : author name = " + author);
        log.debug("in the book search controller method : category = " + categoryId);

        if(!StringUtils.isEmpty(search) || !StringUtils.isEmpty(author) || categoryId != null) {

            response.addObject("bookNameSearch", search);
            response.addObject("authorSearch", author);
            response.addObject("categorySearch", categoryId);


            if (!StringUtils.isEmpty(search)) {
                search = "%" + search + "%";
            }
            if (!StringUtils.isEmpty(author)) {
                author = "%" + author + "%";
            }


            // we only want to do this code if the user has entered either a book name or a author name or a category
            List<Book> books = bookDAO.findByBookNameorAuthororCategory(search,author,categoryId);


            response.addObject("bookVar", books);


            for (Book Book : books) {
                log.debug("book: id = " + Book.getId() + " name = " + Book.getName() + "Author=" +Book.getAuthor());
            }
        }

        return response;
    }
    @GetMapping("/home/homesearch")
    public ModelAndView authsearch(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer categoryId) {
        ModelAndView response = new ModelAndView("home/homesearch");

        log.debug("in the book search controller method : book name = " + search);
        log.debug("in the book search controller method : author name = " + author);
        log.debug("in the book search controller method : category = " + categoryId);

        if(!StringUtils.isEmpty(search) || !StringUtils.isEmpty(author) || categoryId != null) {

            response.addObject("bookNameSearch", search);
            response.addObject("authorSearch", author);
            response.addObject("categorySearch", categoryId);


            if (!StringUtils.isEmpty(search)) {
                search = "%" + search + "%";
            }
            if (!StringUtils.isEmpty(author)) {
                author = "%" + author + "%";
            }


            // we only want to do this code if the user has entered either a book name or a author name or a category
            List<Book> books = bookDAO.findByBookNameorAuthororCategory(search,author,categoryId);


            response.addObject("bookVar", books);


            for (Book Book : books) {
                log.debug("book: id = " + Book.getId() + " name = " + Book.getName() + "Author=" +Book.getAuthor());
            }
        }

        return response;
    }



    @GetMapping("/book/display")
    public ModelAndView displaySearchResults(@RequestParam(required = false) String search,
                                             @RequestParam(required = false, defaultValue = "1") int page) {
        ModelAndView response = new ModelAndView("book/display");

        log.debug("In display Book with no args - log.debug");


        int itemsPerPage = 100;

        List<Book> allBooks = bookDAO.findByBookReservationStatus();


        // Calculate pagination parameters
        int totalBooks = allBooks.size();
        int totalPages = (int) Math.ceil((double) totalBooks / itemsPerPage);

        // Calculate the start and end indices for the current page
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalBooks);

        // Get the sublist of books for the current page
        List<Book> booksForPage = allBooks.subList(startIndex, endIndex);



        response.addObject("bookVar", booksForPage);
        //response.addObject("currentPage", page);
        response.addObject("totalPages", totalPages);

        return response;
    }






    @RequestMapping("/book/detail")
    public ModelAndView detail(@RequestParam Integer id) {
        ModelAndView response = new ModelAndView("book/detail");
        Reservation reservation = new Reservation();
        Book book = bookDAO.findById(id);

        if ( book == null ) {
            log.warn("book with id " + id + " was not found");
            // in a real application you might redirect to a 404 here because the book was not found
            response.setViewName("redirect:/error/404");
            return response;
        }
        response.addObject("reservation", reservation);
        response.addObject("book", book);

        return response;
    }


    @GetMapping("/book/delete{id}")
    public ModelAndView deleteBooks(@RequestParam(required = false) String search,@RequestParam Integer bookId,
                                    @RequestParam(required = false, defaultValue = "1") int page) {
        ModelAndView response = new ModelAndView("book/display");

        log.debug("In display Book with no args - log.debug");

        Book book=bookDAO.findById(bookId);
        bookDAO.delete(book);
        response.addObject("successMessage", "Unreserved  successful!");
        int itemsPerPage = 100;

        List<Book> allBooks = bookDAO.findByBookReservationStatus();


        // Calculate pagination parameters
        int totalBooks = allBooks.size();
        int totalPages = (int) Math.ceil((double) totalBooks / itemsPerPage);

        // Calculate the start and end indices for the current page
        int startIndex = (page - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalBooks);

        // Get the sublist of books for the current page
        List<Book> booksForPage = allBooks.subList(startIndex, endIndex);



        response.addObject("bookVar", booksForPage);
        //response.addObject("currentPage", page);
        response.addObject("totalPages", totalPages);

        return response;
    }


}


