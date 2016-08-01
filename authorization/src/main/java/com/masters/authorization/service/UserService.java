package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface UserService {
	int insertUser(User user) throws MySQLIntegrityConstraintViolationException;
	void updateUser(User user);
	User getUser(int userId);
	User getUser(String key, String password);
	User getUser(String username);	
	List<User> getAllUsers();
}