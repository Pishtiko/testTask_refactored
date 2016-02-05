package com.akvelon.secure.service;

import com.akvelon.secure.entity.Product;
import com.akvelon.secure.entity.User;
import com.akvelon.secure.entity.UserRole;
import com.akvelon.secure.entity.enums.UserRoleEnum;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class AdminDao {


    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> getUsers()
    {
        List<User> users = null;
        users = (List<User>) entityManager
                .createQuery("FROM User");
        return users;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public User getUserById(String userName)   // HAS ISSUE
    {
        User user = new User();
        final String query = "FROM User WHERE user_name = "+userName;
        user = (User) entityManager
                .createQuery(query)
                .getSingleResult();
        return user;
    }

    @SuppressWarnings("unchecked")
    @Transactional
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

    @Transactional
    public boolean createUser(User user, String role)  // HAS ISSUE
    {
        boolean hasErrors = false;
        try {
            UserRole userRole = new UserRole();
            userRole.setUserName(user.getLogin());
            userRole.setRoleName(UserRoleEnum.valueOf(role));
            entityManager.persist(user);
        }catch (Exception e){
            hasErrors = true;
            System.out.println("Exception during persisting "+ e);
        }
        return hasErrors;
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public boolean deleteUser(User user)
    {
        throw new NotImplementedException();
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
