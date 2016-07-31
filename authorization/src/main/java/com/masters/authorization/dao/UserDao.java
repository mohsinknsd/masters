package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface UserDao {
	int insertUser(User user) throws MySQLIntegrityConstraintViolationException;
	void updateUser(User user);
	User getUser(String email, String password);
	User getUser(String username);
	User getUser(int userId);
	List<User> getAllUsers();
}
