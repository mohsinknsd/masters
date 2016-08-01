package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.SessionDao;
import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("SessionService")
@Transactional("authTransactionManager")
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionDao sessionDao;	

	@Override
	public int insertSession(Session session) throws MySQLIntegrityConstraintViolationException {
		return sessionDao.insertSession(session);
	}

	@Override
	public void deleteSession(Session session) {
		sessionDao.deleteSession(session);
	}

	@Override
	public List<Session> getSessions(int userId) {
		return sessionDao.getSessions(userId);
	}
}