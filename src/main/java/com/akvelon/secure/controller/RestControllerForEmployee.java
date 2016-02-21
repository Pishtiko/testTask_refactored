package com.akvelon.secure.controller;


import com.akvelon.secure.entity.OrderEntity;
import com.akvelon.secure.entity.OrderProduct;
import com.akvelon.secure.entity.enums.OrderStatus;
import com.akvelon.secure.service.dao.CustomerDao;
import com.akvelon.secure.service.dao.GenericDaoImpl;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@Transactional
@Secured(value = {"ROLE_USER", "ROLE_ADMIN"})
@RequestMapping(value = "/service")
public class RestControllerForEmployee {

    @Autowired
    private CustomerDao dao = new CustomerDao();

    @Autowired
    private GenericDaoImpl<OrderEntity, Integer> oeDao = new GenericDaoImpl<>(OrderEntity.class);

    @Autowired
    private GenericDaoImpl<OrderProduct, Integer> opDao = new GenericDaoImpl<>(OrderProduct.class);



    @Autowired
    HttpServletRequest request ;


    @RequestMapping( value = "/getOrderList", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderEntity> getOrderList()
    {
        return dao.getOrderList();
    }

    @RequestMapping( value = "/getOrdersOfCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderEntity> getOrdersByCustomerId(@PathVariable String id)
    {
        return dao.getOrdersOrderedByParam("userId", id);
    }

    @RequestMapping( value = "/confirmOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public String confirmOrder(@PathVariable int orderId)
    {
        OrderEntity order = dao.getOrderEntityById(orderId);
        order.setStatus(OrderStatus.CONFIRMED);
        if(!oeDao.persistEntity(order))
            return "order confirmed";
        return "failed to confirm";
    }

    @RequestMapping( value = "/updateOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public String updateOrder(@PathVariable int orderId, @RequestBody List<OrderProduct> orders)
    {
        String resultStatus = "ok";
        try {

            for (OrderProduct op : orders) {
                if (op.getIdd().getIdd() == orderId)
                {
                    OrderProduct oldorder = dao.getOrderProduct(orderId);
                    oldorder.setProductId(op.getProductId());
                    oldorder.setCount(op.getCount());
                    oldorder.setIdd(op.getIdd());
                    opDao.saveOrUpdate(oldorder);
                }
                else {
                    resultStatus = "Id doesn't match ones in changed object: "+op.toString()+": "+op.getIdd().getIdd();
                    System.out.println(resultStatus);
                }
            }

        }catch (Exception e){
            resultStatus = "Couldn't update order "+ e.getStackTrace();
            System.out.println(resultStatus);
        }
        return resultStatus;
    }

    @RequestMapping( value = "/cancelOrder/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public boolean cancelOrder(@PathVariable int orderId)
    {
        boolean hasErrors = false;
        OrderEntity order = oeDao.getById(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        oeDao.saveOrUpdate(order);
        return hasErrors;
    }


}