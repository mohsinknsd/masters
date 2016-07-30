package com.masters.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="service")
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceId", nullable = false)
	private int serviceId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "alias", nullable = true)
	private String alias;
	
	@Column(name = "status", nullable = false)
	private byte status;

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
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

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", "
				+ (name != null ? "name=" + name + ", " : "")
				+ (alias != null ? "alias=" + alias + ", " : "") + "status="
				+ status + "]";
	}
}