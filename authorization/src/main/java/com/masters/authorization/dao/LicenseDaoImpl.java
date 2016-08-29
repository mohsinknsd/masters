package com.masters.authorization.dao;

import org.springframework.stereotype.Repository;

import com.masters.authorization.model.License;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("LicenseDao")
public class LicenseDaoImpl extends AbstractDao implements LicenseDao {

	@Override
	public int insertLicense(License license) throws MySQLIntegrityConstraintViolationException {
		return insertLicense(license);
	}

	@Override
	public License getLicense(int licenseId) {
		return getLicense(licenseId);
	}

	@Override
	public void deleteLicense(License license) {	
		delete(license);
	}
}