package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.Status;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface StatusDao {
	int insertRole(Status status) throws MySQLIntegrityConstraintViolationException;	
	Status getStatus(int statusId);
	List<Status> getAllStatus();
}