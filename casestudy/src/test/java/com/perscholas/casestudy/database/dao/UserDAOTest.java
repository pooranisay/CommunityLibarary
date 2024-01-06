
package com.perscholas.casestudy.database.dao;

import org.junit.jupiter.api.*;
import com.perscholas.casestudy.database.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    @Order(1)
    public void createUserTest() {
        // given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("testpassword");
        user.setCreateDate(new Date()); // You might want to set a valid date here
        // Set other user properties as needed

        // when
        user = userDAO.save(user);

        // then
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals("test@example.com", user.getEmail());
        Assertions.assertEquals("testpassword", user.getPassword());
        Assertions.assertNotNull(user.getCreateDate());
        // Add assertions for other properties

    }

    @Test
    @Order(2)
    public void updateUserTest() {
        // given
        User user = userDAO.findByEmailIgnoreCase("test@example.com");
        Assertions.assertNotNull(user, "User should not be null for update");

        // when
        user.setPassword("newpassword");
        userDAO.save(user);

        // then
        User updatedUser = userDAO.findByEmailIgnoreCase("test@example.com");
        Assertions.assertEquals("newpassword", updatedUser.getPassword());
    }

    @Test
    @Order(3)
    public void findByEmailTest() {

        // given
        String email = "test@example.com";

        // when
        User user = userDAO.findByEmailIgnoreCase(email);

        // then
        Assertions.assertNotNull(user);
        Assertions.assertEquals("test@example.com", user.getEmail());

    }

    @Test
    @Order(4)
    public void deleteUserTest() {
        // given
        String email = "test@example.com";

        // when
        int deleted = userDAO.deleteByEmailIgnoreCase(email);

        // then
        Assertions.assertEquals(1, deleted);

    }

}


