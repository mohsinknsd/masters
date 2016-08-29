package com.masters.authorization.service;

import com.masters.authorization.model.License;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface LicenseService {
	int insertLicense(License license) throws MySQLIntegrityConstraintViolationException;	
	License getLicense(int licenseId);
	void deleteLicense(License license);
}