package com.convenio.dal.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;

public abstract class BaseDao {
	private Session session;
	   

	public Session getSession() {
		//get current session
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		//check if there is a session, if not, it will be created and opened
		if (session == null || !session.isOpen()){
			session = HibernateUtil.getSessionFactory().openSession();
			session.setFlushMode(FlushMode.COMMIT);
		}
		
		return session;
	}
}
