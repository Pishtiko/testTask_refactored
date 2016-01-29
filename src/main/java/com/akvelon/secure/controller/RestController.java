package com.akvelon.secure.controller;


import com.akvelon.secure.entity.OrderModel;
import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.DataAcesObject;
import com.akvelon.secure.entity.Product;
import com.akvelon.secure.service.UserService;
import com.akvelon.secure.service.UserServiceImpl;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@Transactional
@RequestMapping(value = "/service")
public class RestController {

    @Autowired
    private DataAcesObject dao = new DataAcesObject();

    @Autowired
    HttpServletRequest request ;


// Test Methods
    @RequestMapping( value = "/kakashkina", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createTestUser(){
        User useryan = new User("kakashkin", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220");
        if(dao.createUser(useryan))
            return "Нормалёк";
        return "АйАйАй";
    }


    @RequestMapping( value = "/tValenka", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String tValenka() {
        System.out.println("первый попал");
        return dao.getProds("price").toString();
    }

    @RequestMapping( value = "/testMest}", method = RequestMethod.GET)
    @ResponseBody
    public String shnyagovit(@AuthenticationPrincipal UserServiceImpl customUser){
        System.out.println("первый попал");
        String name = dao.getAuthenticatedUser();
        return name;
    }

    @RequestMapping( value = "/testShmest", method = RequestMethod.GET)
    @ResponseBody
    public String shnyag(@AuthenticationPrincipal User customUser){
        return customUser.getLogin().toString();
    }

    // End Test Methods

    @RequestMapping(value="createUser", method = RequestMethod.POST)
    public boolean createUser(@RequestBody User user){
       return dao.createUser(user);
   }


    //<editor-fold defaultstate="collapsed" desc="COMMON METHODS(for MANAGER and CUSTOMER)">


    @RequestMapping( value = "/getProductList/{pop}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProductListJSON(@PathVariable String pop){
        return dao.getProductList(pop);
    }


    @RequestMapping( value = "/getProductById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product getProductByIdXML(@PathVariable int id) {
        return dao.getProductById(id);
    }


    @RequestMapping( value = "/findProduct/{searchKey}/{pop}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> findProduct(@PathVariable String searchKey, @PathVariable String pop)
    {
        return dao.findProductsByName(searchKey);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MANAGER's METHODS">


    @RequestMapping( value = "/getOrdersByCustomerIdXML/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderModel> getOrdersByCustomerIdXML(@PathVariable int id)
    {
        return dao.getOrdersOrderedByParam("CUSTOMERID", Integer.toString(id));
    }


    @RequestMapping( value = "/getOrderListXML")
    @ResponseBody
    public List<OrderModel> getOrderListXML()
    {
        return dao.getOrderList();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CUSTOMER's METHODS">

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ADMIN's METHODS">
    //@PermitAll

    @RequestMapping( value = "/admin/getUserList")
    @ResponseBody
    public List<User> getUsers()
    {
        return dao.getUsers();
    }

    //ISSUE
    @RequestMapping( value = "/admin/createUser/{userName}/{userPassword}/{userRole}", method = RequestMethod.POST)
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

        if (!dao.createUser(user)) {
            return "{\"status\":\"ok\"}";
        } else {
            return "{\"status\":\"not ok\"}";
        }

    }


    @RequestMapping( value = "/admin/deleteUser/{userName}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser (@PathVariable String userName)
    {
        User user = dao.getUserById(userName);

        if (!dao.deleteUser(user)) {
            return "{\"status\":\"ok\"}";
        } else {
            return "{\"status\":\"not ok\"}";
        }

    }

    //</editor-fold>

}