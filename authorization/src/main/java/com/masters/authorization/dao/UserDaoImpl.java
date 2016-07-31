package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("UserDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Override
	public int insertUser(User user) throws MySQLIntegrityConstraintViolationException {		
		return save(user);
	}
	
	@Override
	public void updateUser(User user) {
		update(user);		
	}	
	
	@Override
	public User getUser(int userId) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("userId", userId));
		return (User) criteria.uniqueResult();
	}
	
	@Override
	public User getUser(String email, String password) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("password", DigestUtils.md5DigestAsHex(password.getBytes())));
		return (User) criteria.uniqueResult();
	}

	@Override
	public User getUser(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		Criteria criteria = getSession().createCriteria(User.class);
		return criteria.list();
	}
}