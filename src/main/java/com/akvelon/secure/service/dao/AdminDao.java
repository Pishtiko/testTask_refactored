package com.akvelon.secure.service.dao;

import com.akvelon.secure.entity.User;
import com.akvelon.secure.entity.UserRole;
import com.akvelon.secure.entity.enums.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
@Transactional
public class AdminDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ShaPasswordEncoder passwordEncoder;

    @Autowired
    PasswordEncoder encoder;

    
    @Transactional
    public List<User> getUsers()
    {
        List<User> users = null;
        users = (List<User>) entityManager
                .createQuery("FROM User").getResultList();
        return users;
    }
    
    @Transactional
    public User getUserById(String userName)
    {
        User user = new User();
        final String query = "FROM User WHERE login = "+"'"+userName+"'";
        user = (User) entityManager
                .createQuery(query)
                .getSingleResult();
        return user;
    }
    
    @Transactional
    public String getUserRole(String userName)
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
    public boolean createUser(User user, String role)
    {
        boolean hasErrors = false;
        try {
            if (entityManager.find(User.class, user.getLogin())==null) {
                User userToPersist = user;
                UserRole userRole = new UserRole();
                userRole.setUserName(user);
                userRole.setRoleName(UserRoleEnum.valueOf(role));
                user.setPassword(encoder.encode(user.getPassword()));
                System.out.println(user.getPassword());
                entityManager.persist(userToPersist);
                entityManager.flush();
                entityManager.persist(userRole);
            }
        }catch (Exception e){
            hasErrors = true;
            System.out.println("Exception during persisting "+ e);
        }
        return hasErrors;
    }

    @Transactional
    public boolean updateUser(User user, String role) {
        boolean hasErrors = false;
        try {
            User userToPersist = user;
            UserRole userRole = new UserRole();
            userRole.setUserName(user);
            userRole.setRoleName(UserRoleEnum.valueOf(role));
            user.setPassword(encoder.encode(user.getPassword()));
            entityManager.merge(userToPersist);
            entityManager.flush();
            entityManager.merge(userRole);
        } catch (Exception e) {
            hasErrors = true;
            System.out.println("Exception during persisting " + e);
        }
        return hasErrors;
    }

    
    @Transactional
    public boolean deleteUser(User user)
    {
        boolean hasErrors = false;
        try {
            entityManager.remove(user);
        }catch (Exception e){
            hasErrors = true;
        }
        return hasErrors;
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<UserRole> getRoles() {
        return entityManager.createQuery("FROM UserRole").getResultList();
    }
}
