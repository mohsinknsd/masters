package com.masters.authorization.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public abstract class AbstractDao {
	
	@Autowired
	@Qualifier("authSessionFactory")
	private SessionFactory authSessionFactory;

	protected Session getSession() {
		return authSessionFactory.getCurrentSession();
	}
	
	public int save(Object entity) throws MySQLIntegrityConstraintViolationException{		
		 return (int) getSession().save(entity);
	}
	
	public void delete(Object entity) {
		getSession().delete(entity); 
	}
	
	public void update(Object entity) {
		getSession().update(entity);
	}
}