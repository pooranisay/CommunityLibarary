package com.perscholas.casestudy.service;

import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.dao.CategoriesDAO;
import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
//import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.CreateBookFormBean;
import com.perscholas.casestudy.formbean.ReserveBookFormBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.perscholas.casestudy.Security.AuthenticatedUserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BookService {

    @Autowired
    private AuthenticatedUserService authenticatedUserService;
    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoriesDAO categoriesDAO;
    public Book createBook(CreateBookFormBean form){
        log.debug("id"+form.getId());
        log.debug("Name"+form.getName());
        log.info("imageurl"+form.getImageUrl());
        log.info("author"+form.getAuthor());
        log.info("category"+ form.getCategoryId());

        Book book=bookDAO.findById(form.getId());

        if(book==null){
            book=new Book();

            User user = authenticatedUserService.loadCurrentUser();

           //book.setUserId(user.getId());


        }
        book.setId(form.getId());
        book.setName(form.getName());
        book.setImageUrl(form.getImageUrl());
        book.setAuthor(form.getAuthor());
        Categories cat=new Categories();
        cat.setId(form.getCategoryId());
        book.setCategory(cat);
        return  bookDAO.save(book);
        //return customer;
    }

    public List<Book> getAllBooks() {
        try {
            return bookDAO.findAll();
        } catch (Exception e) {
          log.error("Error getting all books: " + e.getMessage());
            // Handle the exception or rethrow it depending on your use case
            return Collections.emptyList();
        }
    }


}
