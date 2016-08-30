package com.masters.authorization.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Feature;
import com.masters.authorization.model.License;
import com.masters.authorization.model.Role;
import com.masters.authorization.model.User;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("LicenseDao")
public class LicenseDaoImpl extends AbstractDao implements LicenseDao {

	@Override
	public int insertLicense(License license) throws MySQLIntegrityConstraintViolationException {
		return save(license);
	}

	@Override
	public License getLicense(int licenseId) {
		Criteria criteria = getSession().createCriteria(License.class);
		criteria.add(Restrictions.eq("licenseId", licenseId));
		return (License) criteria.uniqueResult();
	}
	
	@Override
	public License getLicense(Feature feature, User user) {	
		Criteria criteria = getSession().createCriteria(License.class);
		criteria.add(Restrictions.eq("user.userId", user.getUserId()));
		criteria.add(Restrictions.eq("feature.featureId", feature.getFeatureId()));
		return (License) criteria.uniqueResult();
	}
	
	@Override
	public License getLicense(Feature feature, Role role) {	
		Criteria criteria = getSession().createCriteria(License.class);
		criteria.add(Restrictions.eq("role.roleId", role.getRoleId()));
		criteria.add(Restrictions.eq("feature.featureId", feature.getFeatureId()));
		return (License) criteria.uniqueResult();
	}

	@Override
	public void deleteLicense(License license) {	
		delete(license);
	}
}