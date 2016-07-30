package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import com.masters.authorization.model.User;

@Repository("UserDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	@Override
	public boolean insertUser(User user) {		
		return save(user);
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