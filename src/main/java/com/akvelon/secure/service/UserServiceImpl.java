package com.akvelon.secure.service;

import com.akvelon.secure.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User getUser(String login) {
        User user;
        final String query = "FROM User p WHERE p.user_name = "+"'"+login+"'";
        user = (User) entityManager
                .createQuery(query)
                .getSingleResult();
        System.out.println(user.toString());
        System.out.println(user.getLogin().toString());

        return user;
    }

}
