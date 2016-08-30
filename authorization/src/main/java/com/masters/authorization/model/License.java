package com.masters.authorization.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="licenses")
public class License {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int licenseId;
	
	@ManyToOne(optional = false, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="featureId")
	private Feature feature;
	
	@ManyToOne(optional = true, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="userId")
	private User user;
	
	@ManyToOne(optional = true, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="roleId")
	private Role role;

	public License() {
		super();
	}

	public License(Feature feature, User user) {
		super();
		this.feature = feature;
		this.user = user;
	}

	public License(Feature feature, Role role) {
		super();
		this.feature = feature;
		this.role = role;
	}	

	public int getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(int licenseId) {
		this.licenseId = licenseId;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "License [licenseId=" + licenseId + ", "
				+ (feature != null ? "feature=" + feature + ", " : "")
				+ (user != null ? "user=" + user + ", " : "")
				+ (role != null ? "role=" + role : "") + "]";
	}	
}