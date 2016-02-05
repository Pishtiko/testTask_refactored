package com.akvelon.secure.service;

import com.akvelon.secure.entity.Product;
import org.hibernate.Criteria;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;


@Repository
@Transactional
public class GenericDAO <T , E> {

    @PersistenceContext
    EntityManager entityManager;

    Class<T> type;

    @Transactional
    public List<T> getList(String order){
        List<T> resultList = (List<T>) entityManager
                .createQuery("FROM "+type.getName());
        return resultList;
    }

    @Transactional
    public List<Product> searchFor(String param, String value){       //////////////////

        final boolean hasParam = param != null;
        /*if (!hasParam || value == null ) {
            // or throw IllegalArgumentException
            return Collections.emptyList();
        }*/

//        System.out.println(entityManager.toString());
         CriteriaBuilder cb = entityManager.getCriteriaBuilder();
         CriteriaQuery<Product> query = cb.createQuery(Product.class);
         Root<Product> root = query.from(Product.class);

        query.where(cb.like(root.<String> get(param), "%"+value.trim()+"%"));

        List<Product> res = entityManager.createQuery(query).getResultList();
        System.out.println("gffdgd");
        for (Product ppp: res ) {
            System.out.println(ppp.getProductName().toString());
        }
        return res;
    }

    @Transactional
    public T getElementById (E itemId, String idColumn) {

        T element;
        final String query = "FROM T WHERE "+idColumn+" = '"+itemId+"'";
        element = (T) entityManager.find(type, itemId);
//                .createQuery(query)
//                .getSingleResult();
        return element;
    }

    @Transactional
    public boolean persistEntity (T entity) {

        boolean hasErrors = false;

        try {
            entityManager.merge(entity);
        }catch (Exception e){
            hasErrors = true;
            System.out.println("Exception thrown persisting "+ e);
        }
        return hasErrors;
    }


}
