package com.akvelon.secure.controller;


import com.akvelon.secure.entity.OrderEntity;
import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.DataAcesObject;
import com.akvelon.secure.entity.Product;
import com.akvelon.secure.service.GenericDAO;
import com.akvelon.secure.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
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
    private GenericDAO<Product,Integer> prDao = new GenericDAO<Product, Integer>();

    @Autowired
    HttpServletRequest request ;


// Test Methods


    @RequestMapping(value = "/azaza", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String azaza(){
        return dao.getAuthenticatedUser().toString();
    }

    @Secured({"ADMIN"})
    @RequestMapping(value = "/poisk", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String poisk(){
        String result = new String();
        List<Product> rr = prDao.searchFor("productName","Ga");
        for (Product p:rr ) {
            result+=p.getProductName().toString();
        }
        String finalResult = result.toString();
        System.out.println(result.toString());
        return result;
    }

    @RequestMapping( value = "/tValenka", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String tValenka() {
        System.out.println("первый попал");
        return dao.getProductListByParam("price").toString();
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





    //<editor-fold defaultstate="collapsed" desc="COMMON METHODS(for MANAGER and CUSTOMER)">


    @RequestMapping( value = "/getProductList/{pop}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProductListJSON(@PathVariable String pop){
        return dao.getProductListByParam(pop);
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
    public List<OrderEntity> getOrdersByCustomerIdXML(@PathVariable int id)
    {
        return dao.getOrdersOrderedByParam("CUSTOMERID", Integer.toString(id));
    }


    @RequestMapping( value = "/getOrderListXML")
    @ResponseBody
    public List<OrderEntity> getOrderListXML()
    {
        return dao.getOrderList();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CUSTOMER's METHODS">

    //</editor-fold>



}