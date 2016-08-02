package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Session;

@Repository("SessionDao")
public class SessionDaoImpl extends AbstractDao implements SessionDao {

	@Override
	public void saveOrUpdateSession (Session session) {		
		saveOrUpdate(session);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Session> getSessions(int userId) {
		Criteria criteria = getSession().createCriteria(Session.class);
		criteria.add(Restrictions.eq("user.userId", userId));
		return criteria.list();
	}

	@Override
	public Session getSession(String trace) {
		Criteria criteria = getSession().createCriteria(Session.class);
		criteria.add(Restrictions.eq("trace", trace));
		return (Session) criteria.uniqueResult();
	}

	@Override
	public Session getSession(int userId, String trace) {
		Criteria criteria = getSession().createCriteria(Session.class);
		criteria.add(Restrictions.eq("user.userId", userId));
		criteria.add(Restrictions.eq("trace", trace));
		return (Session) criteria.uniqueResult();		
	}
	
	@Override
	public void deleteSession(Session session) {
		delete(session);
	}
	
	@Override
	public void deleteSessions(int userId) {	
		String hql = "delete from " + "sessoins" + "where userId = :userId";
		getSession().createQuery(hql).setInteger("userId", userId).executeUpdate();
	}
}