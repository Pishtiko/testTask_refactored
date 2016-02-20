package com.akvelon.secure.service.dao;

import com.akvelon.secure.entity.Product;
//import jdk.nashorn.internal.codegen.types.Type;
import org.hibernate.Criteria;
//import org.hibernate.search.jpa.FullTextEntityManager;
//import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.Type;


@Repository
//@Transactional
public class GenericDaoImpl<T, E> implements org.springframework.data.repository.Repository<T, Class<E>> {
    public GenericDaoImpl(final Class<T> type) {
        this.type = (Class<T>)type;
    }

    public GenericDaoImpl() {
    }

    @PersistenceContext
    EntityManager entityManager;

    Class<T> type;


    @Transactional
    public List<T> getList(Class<T> clazz) {
        System.out.println(clazz.getName());
        System.out.println(clazz.getTypeName());
        System.out.println(clazz.getSimpleName());
        List<T> resultList = (List<T>) entityManager
                .createQuery("FROM " + clazz.getSimpleName()).getResultList();
        return resultList;
    }

    @Transactional
    public List<T> searchFor(String param, String value, Class<T> clazz) {       //////////////////

        final boolean hasParam = param != null;

        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> query = cb.createQuery(clazz);
        final Root<T> root = query.from(clazz);

        query.where(cb.like(root.<String>get(param), "%" + value.trim() + "%"));

        final TypedQuery<T> q = entityManager.createQuery(query);
        List<T> resultList = q.getResultList();
        for (T ppp : resultList) {
            System.out.println(ppp.toString());
        }
        return resultList;
    }

    @Transactional
    public T getById(E itemId){//, String idColumn) {

        T element;
//        final String query = "FROM T WHERE " + idColumn + " = '" + itemId + "'";
        element = (T) entityManager.find(type, itemId);
//                .createQuery(query)
//                .getSingleResult();
        return element;
    }

    @Transactional
    public boolean persistEntity(T entity) {

        boolean hasErrors = false;

        try {
            entityManager.persist(entity);
            entityManager.flush();
        } catch (Exception e) {
            hasErrors = true;
            System.out.println("Exception thrown persisting " + e);
        }finally {
//            entityManager.close();
        }
        return hasErrors;
    }

    @Transactional
    public T saveOrUpdate( T entity) {
        return entityManager.merge(entity);
    }


}
