package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.StatusDao;
import com.masters.authorization.model.Status;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("StatusService")
@Transactional("authTransactionManager")
public class StatusServiceImpl implements StatusService {
	
	@Autowired
	private StatusDao statusDao;

	@Override
	public int insertStatus(Status status) throws MySQLIntegrityConstraintViolationException {
		return statusDao.insertStatus(status);
	}

	@Override
	public Status getStatus(int statusId) {
		return statusDao.getStatus(statusId);
	}

	@Override
	public List<Status> getAllStatus() {	
		return statusDao.getAllStatus();
	}
}