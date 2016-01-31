
package com.akvelon.secure.service;

import com.akvelon.secure.entity.*;
import com.akvelon.secure.entity.enums.UserRoleEnum;
import com.akvelon.secure.util.HibernateUtil;
import com.akvelon.secure.util.HibernateUtil_SecurityDb;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
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

    //HELPERS

    @SuppressWarnings("unchecked")
    @Transactional
    public String getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        System.out.println("User is " + username);
        return username;
    }


    @SuppressWarnings("unchecked")
    @Transactional
    public List<Product> getProds(String pop) {
        System.out.println("А здеся?");
        List<Product> prl = entityManager.createQuery("from Product order by "+pop).getResultList();
        for ( Product pp : prl ) {
            System.out.println( pp.toString());
            System.out.println( pp.getProductName().toString());
        }

        return entityManager.createQuery("from Product order by "+pop).getResultList();
    }

    @PersistenceContext
    private EntityManager entityManager;


//    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//    SessionFactory securityDbSessionFactory = HibernateUtil_SecurityDb.getSessionFactory();

    
    enum allowedPathParams {PRICE,ProductName}
    enum restrictedPathParams {create, delete, update}

    //SECURITY (ADMIN'S ACTIONS)

    public List<User> getUsers()
    {
        List<User> users = null;
        users = (List<User>) entityManager
                .createQuery("FROM User");
//
       return users;
    }
    
    public User getUserById(String userName)   // HAS ISSUE
    {
        User user = new User();
        final String query = "FROM User WHERE user_name = "+userName;
        user = (User) entityManager
                .createQuery(query)
                .getSingleResult();
        return user;
        
    }

    public String getUserRole(String userName)   // HAS ISSUE
    {
        UserRole userRole;
        String role;
        final String query = "FROM UserRole ur WHERE ur.userName = '"+userName+"'";
        userRole = (UserRole) entityManager
                .createQuery(query)
                .getSingleResult();
        role = userRole.getRoleName().toString();
        System.out.println(role);
        return role;

    }

    public boolean createUser(User user)  // HAS ISSUE
    {
        
        boolean hasErrors = false;
        
        try {
            entityManager.persist(user);
        }catch (Exception e){
            hasErrors = true;
            System.out.println("Exception during persisting "+ e);
        }


        return hasErrors;
    }

    public boolean deleteUser(User user)
    {
        throw new NotImplementedException();
    }
            
    
    
    //"CUSTOMERS ACTIONS"

    enum ProductOrderParam{price, ProductName}
    
    public List<Product> getProductList(String pop)
    {       
        List<Product> products = null;
        throw new NotImplementedException();
    }
    
    public List<Product> findProductsByName(String searchKey)
    {       
        List<Product> products = null;
        final String query = "FROM Product WHERE productName LIKE" + " :SEARCHKEY";
        products = (List<Product>) entityManager
                .createQuery(query)
                .setParameter("SEARCHKEY","%" + searchKey + "%");
        return products;
    }
    
    public Product getProductById(int id)
    {
        Product product = null;
        final String query = "FROM Product WHERE id = :ProductId";
        product = (Product) entityManager
                .createQuery(query)
                .setParameter("ProductId", id)
                .getSingleResult();
        return product;
    }
    
    public OrderModel getOrderbyId(int id)
    {
        OrderModel order = null;

        final String query = "FROM OrderModel WHERE id = :ORDERID";
        order = (OrderModel)entityManager
                .createQuery(query)
                .setParameter("ORDERID", id)
                .getSingleResult();

        return order;
        
    }
    
    public List<OrderModel> getOrderList()
    {
        List<OrderModel> orders = null;
        orders = (List<OrderModel>) entityManager
                .createQuery("FROM OrderModel");
       return orders;
    }
    
    public List<OrderModel> getOrdersOrderedByParam(String selectParam, String value )
    {
        List<OrderModel> orders = null;
        String query = "FROM OrderModel WHERE " + selectParam + "= :" + selectParam;
        orders = (List<OrderModel>)entityManager
                    .createQuery(query)
                    .setParameter(selectParam, Integer.parseInt(value));
        return orders;
    }
    
    public int getCurrentCustomerId() // HAS ISSUE
    {
       throw new NotImplementedException();
    }
    
    public List<OrderProduct> getOrdersByCustomerId() // HAS ISSUE
    {
      return Collections.EMPTY_LIST;   // TEMPORARY - MUST CHANGE
    }
    
    
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
    
     public boolean saveOrder(OrderModel order)
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
}