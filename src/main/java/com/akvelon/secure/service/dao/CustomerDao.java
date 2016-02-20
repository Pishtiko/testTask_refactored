package com.akvelon.secure.service.dao;

import com.akvelon.secure.entity.OrderEntity;
import com.akvelon.secure.entity.OrderProduct;
import com.akvelon.secure.entity.Product;
import com.akvelon.secure.entity.UserCart;
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
                .setParameter("SEARCHKEY","%" + searchKey + "%");
        return products;
    }


    //  ACTIONS WITH CART


    @Transactional
    public UserCart getCart(String username) {
        UserCart userCart = entityManager.find(UserCart.class, username);
        if(userCart.getOrderId()==null){
            OrderEntity orderEntity = new OrderEntity();
            entityManager.persist(orderEntity);
            entityManager.flush();
            userCart.setOrderId(orderEntity);
        }
        return userCart;
    }

    @Transactional
    public List<OrderProduct> getCartContent(String username) {
        UserCart uCart = entityManager.find(UserCart.class, username);
        entityManager.refresh(uCart);
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
    public boolean addToCart(Product productId, int count) {                    //  HAS MANY ISSUES
        boolean hasErrors = false;
        try {
            OrderProduct op = new OrderProduct();
            UserCart userCart = entityManager.find(UserCart.class, Helper.getCurrentUsersName());
            OrderEntity oe = userCart.getOrderId();
            op.setProductId(productId);
            op.setCount(count);
//            entityManager.persist(oe);
//            entityManager.flush();
//            entityManager.refresh(oe);
            op.setIdd(oe);
            entityManager.persist(op);
            entityManager.flush();


        } catch (Exception e) {
            hasErrors = true;
        }
        return hasErrors;
    }

    @Transactional
    public boolean removeFromCart(Product productId) {
        boolean hasErrors = false;
        try {
        List<OrderProduct> orderProducts = getMyCartContent();
        for (OrderProduct op: orderProducts  ) {
            if(op.getProductId()==productId)
                entityManager.remove(op);
        }

        }catch (Exception e){
            System.out.println(e+" while removing from the cart");
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
        String query = "FROM OrderProduct op WHERE op.idd = :orderId";
        return entityManager.createQuery(query).setParameter(orderId, "orderId").getResultList();
    }

    @Transactional
    public List<OrderEntity> getOrderList() {
        List<OrderEntity> orders = null;
        orders = (List<OrderEntity>) entityManager
                .createQuery("FROM OrderEntity");
        return orders;
    }

    @Transactional
    public List<OrderEntity> getOrdersOfCustomer(String userName) {
        List<OrderEntity> orders;
        String query = "SELECT op.orderId FROM OrderEntity op WHERE op.userId = :userName";
        orders = (List<OrderEntity>) entityManager
                .createQuery(query)
                .setParameter(userName, userName);
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
     /*   List<OrderEntity> orders = null;
        String query = "FROM OrderEntity WHERE " + selectParam + "= :" + selectParam;
        orders = (List<OrderEntity>) entityManager
                .createQuery(query)
                .setParameter(selectParam, Integer.parseInt(value));*/
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
    public List<Product> orderDetails(int orderId) {                                       // TODO: CHECK
        String queryOp = "SELECT op FROM OrderProduct op WHERE op.idd.idd = :orderId";
        List<OrderProduct> ops = entityManager.createQuery(queryOp)
                .setParameter("orderId", orderId)
                .getResultList();
        List<Product> result = ops.stream().map(OrderProduct::getProductId).collect(Collectors.toList());
        return result;

    }

    @Transactional
    public boolean makeOrder() {
        try {
            UserCart cart = getMyCart();
            OrderEntity order = entityManager.find(OrderEntity.class, cart.getOrderId().getIdd());
            order.setStatus(OrderStatus.UNCONFIRMED);
            entityManager.merge(order);
            cart.setOrderId(null);
        }catch (Exception e){
            System.out.println("failed to purchas an order. "+e.getStackTrace());
        }


        throw new NotImplementedException();
    }

    //  OTHER METHODS


    @Transactional
    public boolean saveOrder(OrderEntity order) {
        boolean hasErrors = false;
        try {
            entityManager.persist(order);
        } catch (Exception e) {
            hasErrors = true;
        }

        return hasErrors;
    }


}

