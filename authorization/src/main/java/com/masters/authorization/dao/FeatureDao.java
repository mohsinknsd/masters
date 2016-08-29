package com.masters.authorization.dao;

import java.util.List;

import com.masters.authorization.model.Feature;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface FeatureDao {
	int insertFeature(Feature feature) throws MySQLIntegrityConstraintViolationException;	
	Feature getFeature(int featureId);
	Feature getFeature(String name);
	void updateFeature(Feature feature);
	List<Feature> getAllFeatures();
}