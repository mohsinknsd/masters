package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.Role;

public interface RoleDao {
	boolean insertRole(Role role);
	Role getRole(String title);
	Role getRole(int roleId);
	List<Role> getAllRoles();
}
