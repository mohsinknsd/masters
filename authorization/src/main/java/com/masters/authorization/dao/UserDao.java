package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.User;

public interface UserDao {
	boolean insertUser(User user);
	User getUser(String email, String password);
	User getUser(String username);
	List<User> getAllUsers();
}
