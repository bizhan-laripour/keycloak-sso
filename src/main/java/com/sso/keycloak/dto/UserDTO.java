package com.sso.keycloak.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	private String emailAddress;

	private String firstName;

	private String lastName;

	private String password;

	private String telephone;

	private String preferredLocal;

	private String accessibleViaMobile;

	private String fax;

	private String mobile;

	private String profile;

	private String accessProfile;

	private String notificationPreference;

//	private ArrayList<String> assignedSecurityRoles;

	public UserDTO()

	{

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPreferredLocal() {
		return preferredLocal;
	}

	public void setPreferredLocal(String preferredLocal) {
		this.preferredLocal = preferredLocal;
	}

	public String getAccessibleViaMobile() {
		return accessibleViaMobile;
	}

	public void setAccessibleViaMobile(String accessibleViaMobile) {
		this.accessibleViaMobile = accessibleViaMobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getAccessProfile() {
		return accessProfile;
	}

	public void setAccessProfile(String accessProfile) {
		this.accessProfile = accessProfile;
	}

	public String getNotificationPreference() {
		return notificationPreference;
	}

	public void setNotificationPreference(String notificationPreference) {
		this.notificationPreference = notificationPreference;
	}

	//	public ArrayList<String> getAssignedSecurityRoles() {
//		return assignedSecurityRoles;
//	}
//
//	public void setAssignedSecurityRoles(ArrayList<String> assignedSecurityRoles) {
//		this.assignedSecurityRoles = assignedSecurityRoles;
//	}
}
