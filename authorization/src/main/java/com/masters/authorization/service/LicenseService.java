package com.masters.authorization.service;

import com.masters.authorization.model.Feature;
import com.masters.authorization.model.License;
import com.masters.authorization.model.Role;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface LicenseService {
	int insertLicense(License license) throws MySQLIntegrityConstraintViolationException;	
	License getLicense(int licenseId);
	License getLicense(Feature feature, User user);
	License getLicense(Feature feature, Role role);
	void deleteLicense(License license);
}