package com.perscholas.casestudy.controller;

import com.perscholas.casestudy.Security.AuthenticatedUserService;
import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.dao.ReserveDAO;
import com.perscholas.casestudy.database.entity.Book;
import com.perscholas.casestudy.database.entity.Categories;
import com.perscholas.casestudy.database.entity.Reservation;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.CreateBookFormBean;
import com.perscholas.casestudy.service.BookService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('ADMIN')")

public class AdminController {
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private BookService bookService;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    private ReserveDAO reserveDAO;

    @GetMapping("/book/create")
    public ModelAndView createBook() {
        ModelAndView response = new ModelAndView("book/create");

        log.debug("In create Book with no args - log.debug");
        log.info("In create Book with no args - log.info");

        return response;
    }
    @PostMapping("/book/createSubmit")
    public ModelAndView createBookSubmit(@Valid CreateBookFormBean form, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            log.info("######################### In create Book submit - has errors #########################");
            ModelAndView response = new ModelAndView("book/create");

            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error: " + error.getDefaultMessage());
            }

            response.addObject("form", form);
            response.addObject("errors", bindingResult);
            return response;
        }

        log.info("######################### In create Book submit - no error found #########################");
        // Handle file upload and set imageUrl in the Book object
        String imageUrl = handleFileUpload(file); // Implement this method to handle file upload and get the URL

        // Set imageUrl in the Book object
        form.setImageUrl(imageUrl);

        Book book = bookService.createBook(form);
        User user = authenticatedUserService.loadCurrentUser();

        bookDAO.save(book);

        // the view name can either be a jsp file name or a redirect to another controller method
        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/book/edit/" + book.getId() + "?success=Book Saved Successfully");

        return response;

    }
    private String handleFileUpload(MultipartFile  file) {
        try {
            // Get the absolute path to the directory where you want to save the file
            String uploadDir = "C:/Users/Anna_/OneDrive/Documents/casestudy/CommunityLibarary/casestudy/src/main/webapp/pub/image/";

            // Create the directory if it doesn't exist
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate a unique filename for the uploaded file
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Create the path to the file
            String filePath = uploadDir + fileName;

            // Save the file to the specified location
            File dest = new File(filePath);
            file.transferTo(dest);

            // Return the relative URL to the saved file
            return "/pub/image/" + fileName;
        } catch (Exception e) {
            // Handle exceptions appropriately (log, throw, etc.)
            e.printStackTrace();
            return null; // Or throw an exception
        }
    }


    @GetMapping("/book/reserveProcess")
    public ModelAndView ReserveProcess(@RequestParam(required = false) String search,
                                       @RequestParam(required = false, defaultValue = "1") int page) {
        ModelAndView response = new ModelAndView("book/reserveProcess");

        log.debug("In display Reservation Process with no args - log.debug");

        int itemsPerPage = 100;

        List<Book> allBooks = bookDAO.getConfirmProcessedBookReservations();

        response.addObject("bookVar", allBooks);


        return response;
    }
    @GetMapping("/book/adminUnReserve")
    public ModelAndView adminUnReserve(
            @RequestParam Integer bookId, @RequestParam Integer reservationId,
            @RequestParam(required = false, defaultValue = "1") int weeksToExtend, HttpServletRequest request)
    {
        log.info("######################### In Book Extend #########################");
        ModelAndView response = new ModelAndView("book/reserveProcess");
        Book book=bookDAO.findById(bookId);
        Reservation reservation = reserveDAO.findById(reservationId);
        reserveDAO.delete(reservation);
        response.addObject("successMessage", "Book Rejected  successful!");
        User user = authenticatedUserService.loadCurrentUser();
        //reservation.setStatus("Available");
        List<Book> allBooks = bookDAO.getConfirmProcessedBookReservations();

        response.addObject("bookVar", allBooks);


        return response;
    }
    @GetMapping("/book/manageReserve")
    public ModelAndView ManageReserve(@RequestParam(required = false) String search,
                                      @RequestParam(required = false, defaultValue = "1") int page) {
        ModelAndView response = new ModelAndView("book/manageReserve");

        log.debug("In display Reservation Process with no args - log.debug");

        int itemsPerPage = 100;

        List<Book> allBooks = bookDAO.getManageProcessedBookReservations();

        response.addObject("bookVar", allBooks);


        return response;
    }
    @GetMapping("/book/edit/{id}")
    // public ModelAndView editBook(@PathVariable int id) {
    public ModelAndView editBook(@PathVariable int id, @RequestParam(required = false) String success) {
        log.info("######################### In /Book/edit #########################");
        ModelAndView response = new ModelAndView("book/create");

        Book book = bookDAO.findById(id);

        if (!StringUtils.isEmpty(success)) {
            response.addObject("success", success);
        }

        CreateBookFormBean form = new CreateBookFormBean();

        if (book != null) {
            form.setId(book.getId());
            form.setName(book.getName());
            form.setAuthor(book.getAuthor());
            form.setImageUrl(book.getImageUrl());
            Categories category = book.getCategory();

            // Assuming Categories has an 'id' property
            if (category != null) {
                Integer categoryId =category.getId();
                category.setId(categoryId);
                form.setCategoryId(category.getId());
            }
        } else {
            log.warn("Book with id " + id+ " was not found");
        }

        response.addObject("form",form) ;
        return response;

    }
}
