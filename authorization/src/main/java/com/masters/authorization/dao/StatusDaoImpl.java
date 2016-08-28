package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Status;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("StatusDao")
public class StatusDaoImpl extends AbstractDao implements StatusDao {	

	@Override
	public int insertStatus(Status status) throws MySQLIntegrityConstraintViolationException {
		return save(status);
	}

	@Override
	public Status getStatus(int statusId) {
		Criteria criteria = getSession().createCriteria(Status.class);
		criteria.add(Restrictions.eq("statusId", statusId));
		return (Status) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Status> getAllStatus() {		
		return  getSession().createCriteria(Status.class).list();
	}
}