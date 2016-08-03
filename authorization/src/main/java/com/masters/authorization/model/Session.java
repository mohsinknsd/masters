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
	
	@Column(name = "client", nullable = false)
	private String client;
	
	@Column(name = "type", nullable = false)
	private String type;
	
	@Column(name = "location", nullable = true)
	private String location;
	
	@Column(name = "trace", nullable = false, unique = true)
	private String trace;
	
	@Column(name = "gcm", nullable = true)
	private String gcm;
	
	@Column(name = "token", nullable = true)
	private String token;
	
	@Column(name = "startedOn", nullable = false)
	private Date startedOn;
	
	@Column(name = "lastUpdatedOn", nullable = false)
	private Date lastUpdatedOn;
	
	@Column(name = "status", nullable = false)
	private byte status;
	
	@ManyToOne(optional = false, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="userId")
	private User user;

	public Session() {
		super();
	}
		
	public Session(HashMap<String, String> map) {
		super();
		this.setClient(map.get("client"));
		this.setType(map.get("type"));
		this.setLocation(map.get("location"));
		this.setTrace(map.get("trace"));
		this.setGcm(map.get("gcm"));
		this.setStartedOn(new Date());
		this.setLastUpdatedOn(new Date());
		this.setStatus((byte) 1);
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
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

	public String getTrace() {
		return trace;
	}

	public void setTrace(String trace) {
		this.trace = trace;
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

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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
		return "Session [sessionId="
				+ sessionId
				+ ", "
				+ (client != null ? "client=" + client + ", " : "")
				+ (type != null ? "type=" + type + ", " : "")
				+ (location != null ? "location=" + location + ", " : "")
				+ (trace != null ? "trace=" + trace + ", " : "")
				+ (gcm != null ? "gcm=" + gcm + ", " : "")
				+ (token != null ? "token=" + token + ", " : "")
				+ (startedOn != null ? "startedOn=" + startedOn + ", " : "")
				+ (lastUpdatedOn != null ? "lastUpdatedOn=" + lastUpdatedOn
						+ ", " : "") + "status=" + status + ", "
				+ (user != null ? "user=" + user : "") + "]";
	}
}