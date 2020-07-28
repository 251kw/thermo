package com.shantery.thermo.userInfo;


import java.io.Serializable;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;


/**
 * From エンティティクラスとは・・・
 */

public class UserInfoForm implements Serializable {

	/** グループID **/
	private String groupId;
	/** グループパスワード **/
	private String groupPassword;
	/** ユーザID **/
	private String userId;
	/** ユーザパスワード **/
	private String userPassword;
	/** ユーザ名 **/
	private String userName;
	/** 性別 **/
	private List<String> sex;
	/** 生年月日 **/
	private String birthday;
	/** 学年区分 **/
	private List<String> SchoolYear;
	/** 管理者フラグ **/
	private List<String> kanri;

	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	

	public List<String> getSex() {
		return sex;
	}

	public void setSex(List<String> sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGroupPassword() {
		return groupPassword;
	}

	public void setGroupPassword(String groupPassword) {
		this.groupPassword = groupPassword;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public List<String> getSchoolYear() {
		return SchoolYear;
	}

	public void setSchoolYear(List<String> schoolYear) {
		SchoolYear = schoolYear;
	}

	public List<String> getKanri() {
		return kanri;
	}

	public void setKanri(List<String> kanri) {
		this.kanri = kanri;
	}
	
}
