package com.masters.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.LicenseDao;
import com.masters.authorization.model.Feature;
import com.masters.authorization.model.License;
import com.masters.authorization.model.Role;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("LicenseService")
@Transactional("authTransactionManager")
public class LicenseServiceImpl implements LicenseService {
	
	@Autowired
	private LicenseDao licenseDao;

	@Override
	public int insertLicense(License license) throws MySQLIntegrityConstraintViolationException {
		return licenseDao.insertLicense(license);
	}

	@Override
	public License getLicense(int licenseId) {
		return licenseDao.getLicense(licenseId);
	}
	
	@Override
	public License getLicense(Feature feature, User user) {			
		return licenseDao.getLicense(feature, user);
	}
	
	@Override
	public License getLicense(Feature feature, Role role) {	
		return licenseDao.getLicense(feature, role);
	}

	@Override
	public void deleteLicense(License license) {
		licenseDao.deleteLicense(license);
	}
}