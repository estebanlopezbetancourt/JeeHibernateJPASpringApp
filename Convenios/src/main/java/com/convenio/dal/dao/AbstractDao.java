package com.convenio.dal.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
public abstract class AbstractDao {
	/*
	 * Notice above, that SessionFactory will be auto-wired here.
	 * This class serve as base class for database related operations.
	 * */
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession() {
    	Session session = sessionFactory.getCurrentSession();
    	session.setFlushMode(FlushMode.COMMIT);
        return session; 
        
    }
 
    public void persist(Object entity) {
        getSession().persist(entity);
    }
 
    public void delete(Object entity) {
        getSession().delete(entity);
    }
}