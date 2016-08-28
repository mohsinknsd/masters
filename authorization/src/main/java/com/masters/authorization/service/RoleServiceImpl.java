package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.RoleDao;
import com.masters.authorization.model.Role;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("RoleService")
@Transactional("authTransactionManager")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override	
	public int insertRole(Role role) throws MySQLIntegrityConstraintViolationException{
		return roleDao.insertRole(role);
	}

	@Override
	public Role getRole(String title) {
		return roleDao.getRole(title);
	}

	@Override
	public Role getRole(int roleId) {
		return roleDao.getRole(roleId);
	}
	
	@Override
	public void updateRole(Role role) {		
		roleDao.updateRole(role);
	}

	@Override
	public List<Role> getAllRoles() {		
		return roleDao.getAllRoles();
	}	
}