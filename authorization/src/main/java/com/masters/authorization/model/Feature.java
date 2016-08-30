package com.masters.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="features")
public class Feature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "featureId", nullable = false)
	private int featureId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "alias", nullable = true)
	private String alias;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "status", nullable = false)
	private byte status;
	
	public Feature() {
		super();		
	}

	public Feature(String name, String alias, String description) {
		super();
		this.name = name;
		this.alias = alias;
		this.description = description;
		this.status = 1;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Feature [featureId="
				+ featureId
				+ ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (alias != null ? "alias=" + alias + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "") + "status=" + status + "]";
	}
}