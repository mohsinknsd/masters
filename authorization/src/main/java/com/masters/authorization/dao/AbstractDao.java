package com.masters.authorization.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractDao {
	
	@Autowired
	@Qualifier("authSessionFactory")
	private SessionFactory authSessionFactory;

	protected Session getSession() {
		return authSessionFactory.getCurrentSession();
	}
	
	public boolean save(Object entity) {		
		 return (Integer) getSession().save(entity) > 0 ? true : false;
	}
	
	public void delete(Object entity) {
		getSession().delete(entity); 
	}
	
	public void update(Object entity) {
		getSession().update(entity);
	}
}