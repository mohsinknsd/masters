package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.Session;
import com.masters.authorization.model.User;

public interface SessionDao {
	boolean insertSession(Session session);
	List<Session> getSessions(User user);	
}
