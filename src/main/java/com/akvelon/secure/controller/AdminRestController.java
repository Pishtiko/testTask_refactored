package com.akvelon.secure.controller;

import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Controller
@Transactional
//@Secured(value = "[ADMIN]")
@RequestMapping(value = "/admin")

public class AdminRestController {

    @Autowired
    HttpServletRequest request ;

    @Autowired
    AdminDao adminDao = new AdminDao();

    @RequestMapping( value = "/addUser/{role}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createUserTest(@PathVariable String role) {

        User user1 = new User("kakashkinina", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220");

        if (adminDao.createUser(user1, role))
            return "Нормалёк";
        return "АйАйАй";
    }

    @RequestMapping( value = "/getUserList")
    @ResponseBody
    public List<User> getUsers()
    {
        return adminDao.getUsers();
    }

    //ISSUE
    @RequestMapping( value = "/createUser/{userName}/{userPassword}/{userRole}", method = RequestMethod.GET)
    @ResponseBody
    public String createUser
    (@PathVariable String userName,
     @PathVariable String userPassword,
     @PathVariable String userRole)
    {
        User user = new User();
        user.setLogin(userName);
        user.setPassword(userPassword);
        // user.setUserRole(userRole);

        if (!adminDao.createUser(user, userRole)) {
            return "{\"status\":\"ok\"}";
        } else {
            return "{\"status\":\"not ok\"}";
        }

    }


    @RequestMapping( value = "/deleteUser/{userName}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser (@PathVariable String userName)
    {
        User user = adminDao.getUserById(userName);

        if (!adminDao.deleteUser(user)) {
            return "{\"status\":\"ok\"}";
        } else {
            return "{\"status\":\"not ok\"}";
        }

    }
}
