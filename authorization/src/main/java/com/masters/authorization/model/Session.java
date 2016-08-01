package com.masters.authorization.model;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sessions")
public class Session {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionId;	
	
	@Column(name = "device", nullable = true)
	private String device;
	
	@Column(name = "type", nullable = true)
	private String type;
	
	@Column(name = "location", nullable = true)
	private String location;
	
	@Column(name = "imei", nullable = true)
	private String imei;
	
	@Column(name = "gcm", nullable = true)
	private String gcm;
	
	@Column(name = "token", nullable = true)
	private String token;
	
	@Column(name = "startedOn", nullable = false)
	private Date startedOn;
	
	@Column(name = "status", nullable = false)
	private byte status;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="userId")
	private User user;

	public Session() {
		super();
	}
		
	public Session(HashMap<String, String> map) {
		super();
		this.setDevice(map.get("device"));
		this.setGcm(map.get("gcm"));
		this.setImei(map.get("imei"));
		this.setLocation(map.get("location"));
		this.setType(map.get("type"));
		this.setStartedOn(new Date());
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getGcm() {
		return gcm;
	}

	public void setGcm(String gcm) {
		this.gcm = gcm;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getStartedOn() {
		return startedOn;
	}

	public void setStartedOn(Date startedOn) {
		this.startedOn = startedOn;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", device=" + device
				+ ", type=" + type + ", location=" + location + ", imei="
				+ imei + ", gcm=" + gcm + ", token=" + token + ", startedOn="
				+ startedOn + ", status=" + status + ", user=" + user + "]";
	}
}