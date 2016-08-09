package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.UserDao;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("UserService")
@Transactional("authTransactionManager")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override	
	public int insertUser(User user) throws MySQLIntegrityConstraintViolationException {
		return userDao.insertUser(user);
	}
	
	@Override
	public void updateUser(User user) {	
		userDao.updateUser(user);
	}

	@Override
	public User getUser(int userId) {		
		return userDao.getUser(userId);
	}

	@Override
	public User getUser(String key, String password) {
		return userDao.getUser(key, password);
	}

	@Override
	public User getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public int getUserCount(String firstname, String lastname) {
		return userDao.getUserCount(firstname, lastname);
	}
}