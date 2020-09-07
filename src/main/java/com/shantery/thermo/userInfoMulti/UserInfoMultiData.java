package com.shantery.thermo.userInfoMulti;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table
class UserInfoMultiData {

	@Id
	@NotEmpty
	private String userId;
	
	private String groupId;
	
	private String userPass;
	
	private String userName;
	
	private String gender;
	
	private String birthday;
	
	private String grade;
	
	private String admin_flg;
	
	private String update_time;
	
	
	UserInfoMultiData(String[] info) {
		this.userId = info[0];
		this.groupId = info[1];
		this.userPass = info[2];
		this.userName = info[3];
		this.gender = info[4];
		this.birthday = info[5];
		this.grade = info[6];
		this.admin_flg = info[7];
		this.update_time = info[8];
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAdmin_flg() {
		return admin_flg;
	}

	public void setAdmin_flg(String admin_flg) {
		this.admin_flg = admin_flg;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
