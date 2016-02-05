
package com.akvelon.secure.service;

import com.akvelon.secure.entity.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class DataAcesObject{

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
//    @Transactional
    public String getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
//        String role = auth.getCredentials().toString();
        String role1 = auth.getAuthorities().toString();
//        System.out.println(role);
        System.out.println(role1);
        System.out.println("User is " + username);
        return username;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Product> getProductListByParam(String op) {
        System.out.println("А здеся?");
        List<Product> products = entityManager.createQuery("from Product order by "+op).getResultList();
        for ( Product pp : products ) {
            System.out.println( pp.toString());
            System.out.println( pp.getProductName().toString());
        }

        return entityManager.createQuery("from Product order by "+op).getResultList();
    }
    public List<Product> getProductListByParamDesc(String op) {
        System.out.println("А здеся?");
        List<Product> products = entityManager.createQuery("from Product order by "+op+" desc").getResultList();

        return products;
    }

 /*   public List<Product> findProductsByName(String searchKey) {
        throw new NotImplementedException();
    }*/

//    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//    SessionFactory securityDbSessionFactory = HibernateUtil_SecurityDb.getSessionFactory();
    
    enum allowedPathParams {PRICE,ProductName}
    enum restrictedPathParams {create, delete, update}
    enum ProductOrderParam{price, ProductName}

    @SuppressWarnings("unchecked")
    @Transactional
    public Product getProductById(int id)
    {
        Product product = null;
        product = entityManager.find(Product.class, id);
        return product;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public OrderEntity getOrderbyId(int id)
    {
        OrderEntity order = null;

        final String query = "FROM OrderEntity WHERE id = :ORDERID";
        order = (OrderEntity)entityManager
                .createQuery(query)
                .setParameter("ORDERID", id)
                .getSingleResult();
        return order;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<OrderEntity> getOrderList()
    {
        List<OrderEntity> orders = null;
        orders = (List<OrderEntity>) entityManager
                .createQuery("FROM OrderEntity");
       return orders;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<OrderEntity> getOrdersOrderedByParam(String selectParam, String value )
    {
        List<OrderEntity> orders = null;
        String query = "FROM OrderEntity WHERE " + selectParam + "= :" + selectParam;
        orders = (List<OrderEntity>)entityManager
                    .createQuery(query)
                    .setParameter(selectParam, Integer.parseInt(value));
        return orders;
    }
    @Transactional
    public List<OrderEntity> getOrdersOrderedByParamDesc(String selectParam, String value )
    {
        List<OrderEntity> orders = null;
        String query = "FROM OrderEntity WHERE " + selectParam + "= :" + selectParam+ " desc";
        orders = (List<OrderEntity>)entityManager
                .createQuery(query)
                .setParameter(selectParam, Integer.parseInt(value));
        return orders;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<OrderEntity> getOrdersOfCustomer(String userName)  {
        List<OrderEntity> orders;
        String query = "SELECT op.orderId FROM OrderEntity op WHERE op.userName = :userName";
        orders = (List<OrderEntity>)entityManager
                .createQuery(query)
                .setParameter(userName, userName);
        return orders;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<OrderEntity> getMyOrders() // HAS ISSUE
    {
        return getOrdersOfCustomer(getAuthenticatedUser());
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public boolean saveOrderProduct(OrderProduct orderProduct)
    {
        boolean hasErrors = false;
      try{
            entityManager.persist(orderProduct);
        }
        catch (Exception e) {
            hasErrors = true;
            System.out.println(this.toString()+" threw "+e);
        }

        return hasErrors;
    }
    @SuppressWarnings("unchecked")
    @Transactional
     public boolean saveOrder(OrderEntity order)
    {
        boolean hasErrors=false;
        try{
            entityManager.persist(order);
        }
        catch (Exception e) {
            hasErrors=true;
        }

        return hasErrors;
    }
    //HELPER METHOD

    private boolean checkRequestHelper(String pathParam)
    {
        if ( allowedPathParams.valueOf(pathParam)!= null 
             && restrictedPathParams.valueOf(pathParam)==null)
            return true;
        else 
            return false;
    }

    //"CUSTOMERS ACTIONS"



//    @SuppressWarnings("unchecked")
//    @Transactional
//    public List<Product> getProductList(String pop)
//    {
//        List<Product> products = null;
//        throw new NotImplementedException();
//    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Product> findProductsByName(String searchKey)
    {
        List<Product> products = null;
        final String query = "FROM Product WHERE productName LIKE" + " :SEARCHKEY";
        products = (List<Product>) entityManager
                .createQuery(query)
                .setParameter("SEARCHKEY","%" + searchKey + "%");
        return products;
    }

//HELPERS

    /*@Transactional
    public boolean incrementStat()
    {
        boolean hasErrors = false;
    User myUser = entityManager.createCritetia(User.class)
            .add(Restrictions.eq("id", username)
                    .setLockMode(LockMode.UPGRADE)
                    .uniqueResult();
    myUser.setAmount(myUser.getAmount() + 100);
    myUser.save();
        return !hasErrors;
    }*/

}