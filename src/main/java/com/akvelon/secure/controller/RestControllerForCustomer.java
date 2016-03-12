package com.akvelon.secure.controller;

import com.akvelon.secure.entity.OrderEntity;
import com.akvelon.secure.entity.OrderProduct;
import com.akvelon.secure.entity.Product;
import com.akvelon.secure.service.dao.CustomerDao;
import com.akvelon.secure.service.dao.enums.ORD;
import com.akvelon.secure.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Controller
@Secured(value = {"ROLE_CUSTOMER", "ROLE_EMPLOYEE", "ROLE_ADMIN"})
@RequestMapping(value = "/main")
public class RestControllerForCustomer {

    @Autowired
    private CustomerDao customerDao = new CustomerDao();



    @RequestMapping( value = "/getProductList/{pop}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProductList(@PathVariable String pop){
        return customerDao.getProductListByParam(pop);
    }


    @RequestMapping( value = "/getProductById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product getProductByIdXML(@PathVariable int id) {
        return customerDao.getProductById(id);
    }


    @RequestMapping( value = "/findProduct/{searchKey}/{pop}", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> findProduct(@PathVariable String searchKey, @PathVariable String pop)
    {
        if(pop=="desc")
        return customerDao.findProductsByName(searchKey, ORD.DESC);
        else return customerDao.findProductsByNameOrDescription(searchKey, ORD.ASC);
    }

    // ACTIONS WITH CART

    @RequestMapping( value = "/getMyCart", method = RequestMethod.GET)
    @ResponseBody
    public OrderEntity getCartId() {
        return customerDao.getMyCart().getOrderId();
    }

    @RequestMapping( value = "/getCartContent", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderProduct> getCartContent() {
        return customerDao.getCartContent(Helper.getCurrentUsersName());

    }

    @RequestMapping( value = "/addToCart/{productId}/{count}", method = RequestMethod.POST)
    @ResponseBody
    public int addToCart(@PathVariable int productId, @PathVariable int count){
       return customerDao.addToCart(productId, count);
    }

    @RequestMapping( value = "/updateCart", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateCart(@RequestBody List<OrderProduct> newCart){
        return customerDao.updateCart(newCart);
    }

    @RequestMapping( value = "/removeFromCart/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean removeFromCart(@PathVariable int productId) {
        return customerDao.removeFromCart(productId);
    }

    @RequestMapping( value = "/cleanTheCart", method = RequestMethod.GET)               //NOT IMPLEMENTED
    @ResponseBody
    public boolean cleanTheCart() {
       return customerDao.cleanTheCart();
    }

    // ACTIONS WITH ORDER

    @RequestMapping( value = "/getMyOrders", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderEntity> getMyOrders() {
        return customerDao.getMyOrders();
    }

    @RequestMapping( value = "/orderDetails/{orderId}", method = RequestMethod.GET)     // Think about security  -  Check if OrderId is of current Customer's Orders.
    @ResponseBody
    public List<OrderProduct> orderDetails(@PathVariable int orderId) {
        return customerDao.orderDetails(orderId);
    }

    @RequestMapping( value = "/makeOrder", method = RequestMethod.POST)
    @ResponseBody
    public boolean makeOrder() {
        return customerDao.makeOrder();
    }

}
