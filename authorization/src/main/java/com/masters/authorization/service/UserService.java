package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.User;

public interface UserService {
	boolean insertUser(User user);
	User getUser(String email, String password);
	User getUser(String username);
	List<User> getAllUsers();
}