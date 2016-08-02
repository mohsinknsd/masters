package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.SessionDao;
import com.masters.authorization.model.Session;

@Service("SessionService")
@Transactional("authTransactionManager")
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionDao sessionDao;	

	@Override
	public void saveOrUpdateSession(Session session) {	
		sessionDao.saveOrUpdateSession(session);
	}

	@Override
	public void deleteSession(Session session) {
		sessionDao.deleteSession(session);
	}
	
	@Override
	public Session getSession(String trace) {
		return sessionDao.getSession(trace);
	}

	@Override
	public Session getSession(int userId, String trace) {
		return sessionDao.getSession(userId, trace);
	}
	
	@Override
	public List<Session> getSessions(int userId) {
		return sessionDao.getSessions(userId);
	}
}