package com.perscholas.casestudy.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import com.perscholas.casestudy.database.dao.UserDAO;
import com.perscholas.casestudy.database.entity.User;
import com.perscholas.casestudy.formbean.RegisteredUserFormBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserService {
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDAO userDAO;
    public User createNewUser(RegisteredUserFormBean form){
        User user=new User();
        user.setEmail(form.getEmail().toLowerCase());
        user.setCreateDate(new Date());
       // user.setRoles();
        String encoded = passwordEncoder.encode(form.getPassword());
        log.debug("Encoded password: " + encoded);
        user.setPassword(encoded);
        return userDAO.save(user);
    }

}
