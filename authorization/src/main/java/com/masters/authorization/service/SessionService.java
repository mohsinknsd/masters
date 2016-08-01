package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.Session;

public interface SessionService {
	void saveOrUpdateSession(Session session);
	void deleteSession(Session session);
	Session getSession(String trace);
	List<Session> getSessions(int userId);
}