package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.Role;

public interface RoleService {
	boolean insertRole(Role role);
	Role getRole(String title);
	Role getRole(int roleId);
	List<Role> getAllRoles();
}