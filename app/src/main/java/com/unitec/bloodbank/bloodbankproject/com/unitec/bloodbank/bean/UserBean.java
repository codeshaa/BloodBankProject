package com.unitec.bloodbank.bloodbankproject.com.unitec.bloodbank.bean;

import java.io.Serializable;
import java.sql.Blob;

public class UserBean implements Serializable{


	private static final long serialVersionUID = 1L;

	private Integer userId;
	private String givenName;
	private String surname;
	private String dob;
	private byte[] profileImg;
	private String gender;
	private String bloodGroup;
	private String phone;
	private String email;
	private String address;
	private String loginName;
	private String password;
	private boolean isDonor;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public byte[] getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(byte[] profileImg) {
		this.profileImg = profileImg;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(int gender) {
		switch (gender)
		{
			case 0:this.gender = "Male";
					break;
			case 1:this.gender = "Female";
					break;
			case 3:this.gender = "Other";
					break;
			default:this.gender = "N/A";
					break;
		}

	}
	public void setGenderAsString(String gender){
		this.gender=gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDonor() {
		return isDonor;
	}
	public void setDonor(boolean isDonor) {
		this.isDonor = isDonor;
	}
	
	
}
