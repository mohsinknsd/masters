package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.UserDao;
import com.masters.authorization.model.User;

@Service("UserService")
@Transactional("authTransactionManager")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override	
	public boolean insertUser(User user) {
		return userDao.insertUser(user);
	}

	@Override
	public User getUser(String email, String password) {
		return userDao.getUser(email, password);
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}