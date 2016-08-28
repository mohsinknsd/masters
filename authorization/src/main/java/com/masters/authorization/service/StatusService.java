package com.masters.authorization.service;

import java.util.List;

import com.masters.authorization.model.Status;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface StatusService {
	int insertStatus(Status status) throws MySQLIntegrityConstraintViolationException;	
	Status getStatus(int statusId);
	List<Status> getAllStatus();
}