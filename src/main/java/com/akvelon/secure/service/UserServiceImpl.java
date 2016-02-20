package com.akvelon.secure.service;

import com.akvelon.secure.entity.User;
import com.akvelon.secure.service.dao.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    StatisticsService statisticsService;


    @Override
    public User getUser(String login) {
        User user;
        final String query = "FROM User p WHERE p.login = "+"'"+login+"'";
        user = (User) entityManager
                .createQuery(query)
                .getSingleResult();
        statisticsService.incrementStat(user);
        System.out.println(user.toString());
        System.out.println(user.getLogin().toString());

        return user;
    }

}
