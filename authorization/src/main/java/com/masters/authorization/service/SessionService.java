package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface SessionService {
	int insertSession(Session session) throws MySQLIntegrityConstraintViolationException;
	void deleteSession(Session session);
	List<Session> getSessions(int userId);
}