package com.perscholas.casestudy.controller;

import com.perscholas.casestudy.database.dao.BookDAO;
import com.perscholas.casestudy.database.entity.Book;
import io.micrometer.common.util.StringUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class AuthController {
    @Autowired
    private AuthenticatedUserService authenticatedUserService;
    @Autowired
    private UserService userService;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private UserDAO userDAO;
    @GetMapping("/auth/login")
    public ModelAndView login() {
        ModelAndView response = new ModelAndView();
        response.setViewName("auth/login");
        return response;
    }

    @GetMapping("/home/home")
    public ModelAndView home() {
        ModelAndView response = new ModelAndView();
        response.setViewName("home/home");
        return response;
    }

    @GetMapping("/home/faq")
    public ModelAndView faq() {
        ModelAndView response = new ModelAndView();
        response.setViewName("home/faq");
        return response;
    }

    @GetMapping("/home/aboutus")
    public ModelAndView aboutus() {
        ModelAndView response = new ModelAndView();
        response.setViewName("home/aboutus");
        return response;
    }

    @GetMapping("/auth/register")
    public ModelAndView register() {
        ModelAndView response = new ModelAndView();
        response.setViewName("auth/register");
        return response;
    }
    @GetMapping("/auth/registerSubmit")
    public ModelAndView registerSubmit(@Valid RegisteredUserFormBean form, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.info("######################### In create user submit - has errors #########################");
            ModelAndView response = new ModelAndView("auth/register");

            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error: " + error.getDefaultMessage());
            }

            response.addObject("form", form);
            response.addObject("errors", bindingResult);
            return response;
        }

        log.info("######################### In create user submit - no error found #########################");

        User c = userService.createNewUser(form);

        authenticatedUserService.authenticateNewUser(session,c.getEmail(),form.getPassword());
        // the view name can either be a jsp file name or a redirect to another controller method
        ModelAndView response = new ModelAndView();
        response.setViewName("redirect:/home/home");

        return response;

    }


}
