package com.masters.authorization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.masters.authorization.dao.FeatureDao;
import com.masters.authorization.model.Feature;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service("FeatureService")
@Transactional("authTransactionManager")
public class FeatureServiceImpl implements FeatureService {
	
	@Autowired
	private FeatureDao featureDao;

	@Override	
	public int insertFeature(Feature feature) throws MySQLIntegrityConstraintViolationException {
		return featureDao.insertFeature(feature);
	}	

	@Override
	public Feature getFeature(int featureId) {
		return featureDao.getFeature(featureId);
	}
	
	@Override
	public Feature getFeature(String name) {
		return featureDao.getFeature(name);
	}
	
	@Override
	public void updateFeature(Feature feature) {		
		featureDao.updateFeature(feature);
	}

	@Override
	public List<Feature> getAllFeatures() {		
		return featureDao.getAllFeatures();
	}	
}