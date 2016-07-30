package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;

@Repository("SessionDao")
public class SessionDaoImpl extends AbstractDao implements SessionDao {

	@Override
	public boolean insertSession(Session session) {		
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