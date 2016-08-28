package com.masters.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roleId", nullable = false)
	private int roleId;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "alias", nullable = true)
	private String alias;
	
	@Column(name = "status", nullable = false)
	private byte status;	

	public Role() {
		super();	
	}
	
	public Role(String title, String alias) {
		super();
		this.title = title;
		this.alias = alias;
		this.status = 1;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
		return "Role [roleId=" + roleId + ", title=" + title + ", alias=" + alias
				+ ", status=" + status + "]";
	}
}