package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("SessionDao")
public class SessionDaoImpl extends AbstractDao implements SessionDao {

	@Override
	public int insertSession(Session session) throws MySQLIntegrityConstraintViolationException {		
		return save(session);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Session> getSessions(User user) {
		Criteria criteria = getSession().createCriteria(Session.class);
		criteria.add(Restrictions.eq("userId", user.getUserId()));
		return criteria.list();
	}
}