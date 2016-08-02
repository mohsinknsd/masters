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

import org.springframework.util.DigestUtils;

import com.masters.utilities.common.DateTimeParser;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;	
	
	@Column(name = "username", nullable = true)
	private String username;
	
	@Column(name = "firstname", nullable = false)
	private String firstname;
	
	@Column(name = "middlename", nullable = true)
	private String middlename;
	
	@Column(name = "lastname", nullable = false)
	private String lastname;
	
	@Column(name = "image", nullable = true)
	private String image;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "gender", nullable = true, columnDefinition = "BIT(1)")
	private boolean gender;
	
	@Column(name = "birthdate", nullable = true)
	private Date birthdate;
		
	@Column(name = "mobile", nullable = true)
	private String mobile;
	
	@Column(name = "phone", nullable = true)
	private String phone;
	
	@Column(name = "address", nullable = true)
	private String address;
	
	@Column(name = "city", nullable = true)
	private String city;
	
	@Column(name = "district", nullable = true)
	private String district;
	
	@Column(name = "state", nullable = true)
	private String state;
	
	@Column(name = "country", nullable = true)
	private String country;
	
	@Column(name = "pincode", nullable = true, columnDefinition = "TINYINT")
	private Integer pincode;
	
	@Column(name = "remarks", nullable = true)
	private String remarks;
	
	@Column(name = "userKey", nullable = true)
	private String userKey;
	
	@Column(name = "registeredOn", nullable = false)
	private Date registeredOn;
	
	@Column(name = "custom", nullable = true)
	private String custom;
	
	@Column(name = "status", nullable = false)
	private byte status;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="roleId")
	private Role role;
	
	public User() { }
	
	public User(int userId) {
		this.userId = userId;
	}
	
	public User(HashMap<String, String> map) {		
		this.setUsername(map.get("firstname") + map.get("lastname"));
		this.setFirstname(map.get("firstname"));
		this.setFirstname(map.get("middlename"));
		this.setLastname(map.get("lastname"));
		this.setImage(map.get("image"));
		this.setEmail(map.get("email"));
		this.setPassword(map.get("password"));
		this.setGender(map.get("gender").trim().equals("1"));
		this.setBirthdate(DateTimeParser.getFormattedDate(map.get("birthdate")));
		this.setMobile(map.get("mobile"));
		this.setPhone(map.get("phone"));
		this.setAddress(map.get("address"));
		this.setCity(map.get("city"));
		this.setDistrict(map.get("district"));
		this.setState(map.get("state"));
		this.setCountry(map.get("country"));
		this.setRemarks(map.get("remarks"));
		this.setCustom(map.get("custom"));
		this.setRegisteredOn(new Date());
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = DigestUtils.md5DigestAsHex(password.getBytes());
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public Date getRegisteredOn() {
		return registeredOn;
	}

	public void setRegisteredOn(Date registeredOn) {
		this.registeredOn = registeredOn;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId="
				+ userId
				+ ", "
				+ (username != null ? "username=" + username + ", " : "")
				+ (firstname != null ? "firstname=" + firstname + ", " : "")
				+ (middlename != null ? "middlename=" + middlename + ", " : "")
				+ (lastname != null ? "lastname=" + lastname + ", " : "")
				+ (image != null ? "image=" + image + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (password != null ? "password=" + password + ", " : "")
				+ "gender="
				+ gender
				+ ", "
				+ (birthdate != null ? "birthdate=" + birthdate + ", " : "")
				+ (mobile != null ? "mobile=" + mobile + ", " : "")
				+ (phone != null ? "phone=" + phone + ", " : "")
				+ (address != null ? "address=" + address + ", " : "")
				+ (city != null ? "city=" + city + ", " : "")
				+ (district != null ? "district=" + district + ", " : "")
				+ (state != null ? "state=" + state + ", " : "")
				+ (country != null ? "country=" + country + ", " : "")
				+ "pincode="
				+ pincode
				+ ", "
				+ (remarks != null ? "remarks=" + remarks + ", " : "")
				+ (registeredOn != null ? "registeredOn=" + registeredOn + ", "
						: "") + "custom=" + custom + ", status=" + status
				+ ", " + (role != null ? "role=" + role : "") + "]";
	}
}