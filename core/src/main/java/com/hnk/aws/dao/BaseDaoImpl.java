package com.hnk.aws.dao;

import com.mysema.query.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author hnguyen
 */
public class BaseDaoImpl {
    @PersistenceContext
    protected EntityManager entityManager;
    protected JPAQuery query;

    /**
     * Get JPA Query.
     *
     * @return
     */
    public JPAQuery getQuery() {
        if (query == null) {
            query = new JPAQuery(entityManager);
        }
        return query;
    }
}
