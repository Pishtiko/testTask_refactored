package com.akvelon.secure.controller;

import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.dao.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;



@Controller
@Transactional
@Secured(value = "ROLE_ADMIN")
@RequestMapping(value = "/admin")

public class RestControllerForAdmin {

    @Autowired
    HttpServletRequest request ;

    @Autowired
    AdminDao adminDao = new AdminDao();


    @RequestMapping( value = "/getUserList")
    @ResponseBody
    public List<User> getUsers()
    {
        return adminDao.getUsers();
    }

    //ISSUE
    @RequestMapping( value = "/createUser/{userRole}", method = RequestMethod.POST)
    @ResponseBody
    public String createUser
    (@RequestBody User user,
     @PathVariable String userRole)
    {
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
