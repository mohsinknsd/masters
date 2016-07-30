package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Role;

@Repository("RoleDao")
public class RoleDaoImpl extends AbstractDao implements RoleDao {

	@Override
	public boolean insertRole(Role role) {
		return save(role);
	}

	@Override
	public Role getRole(String title) {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("title", title));
		return (Role) criteria.uniqueResult();
	}

	@Override
	public Role getRole(int roleId) {
		Criteria criteria = getSession().createCriteria(Role.class);
		criteria.add(Restrictions.eq("roleId", roleId));
		return (Role) criteria.uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles() {
		Criteria criteria = getSession().createCriteria(Role.class);
		return criteria.list();
	}
}