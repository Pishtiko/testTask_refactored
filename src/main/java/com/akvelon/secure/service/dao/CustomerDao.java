package com.akvelon.secure.service.dao;

import com.akvelon.secure.entity.*;
import com.akvelon.secure.entity.enums.OrderStatus;
import com.akvelon.secure.service.dao.enums.ORD;
import com.akvelon.secure.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    GenericDaoImpl<OrderEntity, Integer> orderDao = new GenericDaoImpl<>();


    //  PRODUCT GETTERS

    @Transactional
    public List<Product> getProductListByParam(String op) {
        System.out.println("А здеся?");
        List<Product> products = entityManager.createQuery("from Product order by " + op).getResultList();

        return entityManager.createQuery("from Product order by " + op).getResultList();
    }

    @Transactional
    public List<Product> getProductListByParamDesc(String op) {
        System.out.println("А здеся?");
        List<Product> products = entityManager.createQuery("from Product order by " + op + " desc").getResultList();

        return products;
    }

    @Transactional
    public Product getProductById(int id){
        return entityManager.find(Product.class, id);
    }


    @Transactional
    public List<Product> findProductsByName(String searchKey, ORD order)
    {
        List<Product> products = null;
        final String query = "FROM Product WHERE productName LIKE" + " :SEARCHKEY"+ " ORDER BY productName "+order.name();
        products = (List<Product>) entityManager
                .createQuery(query)
                .setParameter("SEARCHKEY","%" + searchKey + "%")
                .getResultList();
        return products;
    }

    @Transactional
    public List<Product> findProductsByNameOrDescription(String searchKey, ORD order)
    {
        List<Product> products = null;
        final String query = "FROM Product WHERE productName OR description LIKE" + " :SEARCHKEY"+ " ORDER BY productName "+order.name();
        products = (List<Product>) entityManager
                .createQuery(query)
                .setParameter("SEARCHKEY","%" + searchKey + "%")
                .getResultList();
        return products;
    }


    //  ACTIONS WITH CART


    @Transactional
    public UserCart getCart(String username) {
        User user = entityManager.find(User.class, username);
        UserCart uc = new UserCart(user, null);
        UserCart userCart = entityManager.find(UserCart.class, uc);
        if(userCart.getOrderId()==null){
            OrderEntity orderEntity = new OrderEntity();
            entityManager.persist(orderEntity);
            entityManager.flush();
            userCart.setOrderId(orderEntity);
            entityManager.merge(userCart);
            entityManager.flush();
        }
        return userCart;
    }

    @Transactional
    public List<OrderProduct> getCartContent(String username) {
        UserCart uCart = entityManager.find(UserCart.class, username);
        if(uCart==null){
            uCart = new UserCart();
            uCart.setUserName(Helper.getCurrentUser());
        }
        if(uCart.getOrderId()==null)
        {
            OrderEntity oe = new OrderEntity();
            oe.setUserId(Helper.getCurrentUser());
            oe.setStatus(OrderStatus.COMPLETED);

            entityManager.persist(oe);
            entityManager.flush();
            entityManager.refresh(oe);
            uCart.setOrderId(oe);
            entityManager.persist(uCart);
        }

        String query = "FROM OrderProduct WHERE idd = "+"'"+uCart.getOrderId().getIdd()+"'";
        List<OrderProduct> cartOrder = entityManager.createQuery(query).getResultList();
        return cartOrder;

    }

    @Transactional
    public UserCart getMyCart() {
        return getCart(Helper.getCurrentUsersName());
    }

    @Transactional
    public List<OrderProduct> getMyCartContent() {
        return getCartContent(Helper.getCurrentUsersName());
    }


    @Transactional
    public int addToCart(int productId, int count) {                    //  HAS MANY ISSUES
            OrderProduct op = new OrderProduct();
            UserCart userCart = getMyCart();
            OrderEntity oe = userCart.getOrderId();
            Product product = entityManager.find(Product.class, productId);
            oe.setUserId(Helper.getCurrentUser());
            oe.setStatus(OrderStatus.IN_CART);
            entityManager.flush();
            op.setProductId(product);
            op.setCount(count);
            op.setIdd(oe);
            entityManager.persist(op);
            return op.getOrderId();
    }

    @Transactional
    public boolean removeFromCart(int orderId) {
        boolean hasErrors = false;
        try {
            OrderProduct op = entityManager.find(OrderProduct.class, orderId);
            if(op.getIdd() == getMyCart().getOrderId()){
                entityManager.remove(op);
            }

        }catch (Exception e){
            System.out.println(e+" while removing from the cart");
        }
        return hasErrors;
    }


    @Transactional
    public boolean updateCart(List<OrderProduct> newCart) {
        boolean hasErrors = false;
        try {
            for (OrderProduct op : newCart){
                entityManager.merge(op);
            }

        }catch (Exception e){
            hasErrors = true;
            System.out.println("failed to save. "+e);
        }
        return hasErrors;
    }

    @Transactional
    public boolean cleanTheCart() {
        boolean hasErrors = false;
        try {
            UserCart cart = getMyCart();
            List<OrderProduct> orderProducts = getMyCartContent();
            entityManager.flush();
            for (OrderProduct op:
                    orderProducts
                    ) {
                entityManager.remove(op);
            }

        }catch (Exception e){
            System.out.println(e+" while cleaning the cart");
            hasErrors = true;
        }

        return hasErrors;

    }

    //  ACTIONS WITH ORDERS

    @Transactional
    public OrderEntity getOrderEntityById(int orderId) {
        return entityManager.find(OrderEntity.class, orderId);
    }

    @Transactional
    public List<OrderProduct> getOrderById(int orderId) {
        String query = "FROM OrderProduct op WHERE op.idd.idd = :orderId";
        return entityManager.createQuery(query).setParameter("orderId",orderId).getResultList();
    }

    @Transactional
    public List<OrderEntity> getOrderList() {
        List<OrderEntity> orders = null;
        orders = (List<OrderEntity>) entityManager
                .createQuery("FROM OrderEntity").getResultList();
        return orders;
    }

    @Transactional
    public List<OrderEntity> getOrdersOfCustomer(String userName) {
        List<OrderEntity> orders;
        String query = "SELECT oe FROM OrderEntity oe WHERE oe.userId.login = :userName";
        orders = (List<OrderEntity>) entityManager
                .createQuery(query)
                .setParameter("userName", userName)
                .getResultList();
        return orders;
    }

    @Transactional
    public List<OrderEntity> getMyOrders()
    {
        return getOrdersOfCustomer(Helper.getCurrentUsersName());
    }

    @Transactional
    public List<OrderEntity> getOrdersOrderedByParam(String selectParam, String value) {

        List<OrderEntity> orders = orderDao.searchFor(selectParam, value, OrderEntity.class);
        return orders;
    }

    @Transactional
    public List<OrderEntity> getOrdersOrderedByParamDesc(String selectParam, String value) {
        List<OrderEntity> orders = null;
        String query = "FROM OrderEntity WHERE " + selectParam + "= :" + selectParam + " desc";
        orders = (List<OrderEntity>) entityManager
                .createQuery(query)
                .setParameter(selectParam, Integer.parseInt(value));
        return orders;
    }


    @Transactional
    public OrderProduct getOrderProduct(int orderId){
        return entityManager.find(OrderProduct.class, orderId);
    }


    @Transactional
    public List<OrderProduct> orderDetails(int orderId) {
        final String queryOp = "SELECT op FROM OrderProduct op WHERE op.idd.idd = :orderId";
        List<OrderProduct> ops = entityManager.createQuery(queryOp)
                .setParameter("orderId", orderId)
                .getResultList();
        return ops;

    }

    @Transactional
    public boolean makeOrder() {
        boolean hasErrors = false;
        try {
            UserCart cart = getMyCart();
            OrderEntity order = cart.getOrderId();
            order.setStatus(OrderStatus.UNCONFIRMED);
            entityManager.persist(order);
            cart.setOrderId(null);
            entityManager.persist(cart);
        }catch (Exception e){
            System.out.println("failed to purchase an order. "+e);
            hasErrors = true;
        }
        return  hasErrors;
    }

    //  OTHER METHODS


    @Transactional
    public boolean saveOrder(OrderEntity order) {
        boolean hasErrors = false;
        try {
            entityManager.merge(order);
        } catch (Exception e) {
            hasErrors = true;
        }

        return hasErrors;
    }



}

