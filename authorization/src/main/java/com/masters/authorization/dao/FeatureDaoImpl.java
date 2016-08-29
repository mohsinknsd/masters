package com.masters.authorization.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.masters.authorization.model.Feature;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Repository("FeatureDao")
public class FeatureDaoImpl extends AbstractDao implements FeatureDao {

	@Override
	public int insertFeature(Feature feature) throws MySQLIntegrityConstraintViolationException {
		return save(feature);
	}	

	@Override
	public Feature getFeature(int featureId) {
		Criteria criteria = getSession().createCriteria(Feature.class);
		criteria.add(Restrictions.eq("featureId", featureId));
		return (Feature) criteria.uniqueResult();
	}
	
	@Override
	public Feature getFeature(String name) {
		Criteria criteria = getSession().createCriteria(Feature.class);
		criteria.add(Restrictions.eq("name", name));
		return (Feature) criteria.uniqueResult();
	}
	
	@Override
	public void updateFeature(Feature feature) {	
		update(feature);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Feature> getAllFeatures() {
		Criteria criteria = getSession().createCriteria(Feature.class);
		return criteria.list();
	}	
}