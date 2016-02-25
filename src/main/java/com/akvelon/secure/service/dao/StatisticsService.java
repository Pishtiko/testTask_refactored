package com.akvelon.secure.service.dao;


import com.akvelon.secure.entity.StatisticEntity;
import com.akvelon.secure.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class StatisticsService {

    @PersistenceContext
    EntityManager entityManager;


    @Transactional
    public void incrementStat(User user) {

        StatisticEntity stat = new StatisticEntity();
        stat.setUserId(user);
        stat.setDateTime(Calendar.getInstance().getTime());
        entityManager.persist(stat);

    }

    @Transactional
    public List<Object> getStat() {
//        final String query = "FROM StatisticEntity";
        final String query =
                "USE [Temporary]" +
                        " SELECT "+
                        " CONVERT(date, t_timestamp) AS date_, "+
                        " COUNT( userName) AS Users" +
                        " FROM StatisticEntity" +
                        " GROUP BY CONVERT(date, t_timestamp)" +
                        " ORDER By CONVERT(date, t_timestamp)";
        List<Object> stats;


        stats =  entityManager
                .createNativeQuery(query)
                .getResultList();
//
//        stats = (List<StatisticEntity>) entityManager
//                .createQuery(query)
//                .getResultList();
        return stats;
    }

    @Transactional
    public List<Object> getStatUnique() {
//        final String query = "FROM StatisticEntity";
        final String query =
                "USE [Temporary]" +
                        " SELECT "+
                        " CONVERT(date, t_timestamp) AS date_, "+
                        " COUNT(DISTINCT userName) AS uniqueUsers" +
                        " FROM StatisticEntity" +
                        " GROUP BY CONVERT(date, t_timestamp)" +
                        " ORDER By CONVERT(date, t_timestamp)";
        List<Object> stats;


        stats =  entityManager
                .createNativeQuery(query)
                .getResultList();
//
//        stats = (List<StatisticEntity>) entityManager
//                .createQuery(query)
//                .getResultList();
        return stats;
    }

    @Transactional
    public List<Object> getGeneralStat() {
        final String hQuery = "SELECT dateTime, count(userId) FROM StatisticEntity group by dateTime";
        final String query =
                "USE [Temporary]" +
                        " SELECT "+
                        " CONVERT(date, t_timestamp) AS date_, "+
                        " COUNT(DISTINCT userName) AS usiqueUsers" +
                        " FROM StatisticEntity" +
                        " GROUP BY CONVERT(date, t_timestamp)" +
                        " ORDER By CONVERT(date, t_timestamp)";
        List<Object> stats;


        stats =  entityManager
                .createQuery(hQuery)
                .getResultList();

        return stats;
    }
}
