package com.shantery.thermo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * user_infoデータベースのエンティティクラス
 */
@Entity
@Table(name = "user_info") 
public class UserInfoEntity{

	/** ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** ユーザID **/
	@Column(length=32)
	private String userId;
	/** グループID **/
	@Column(length=32)
	private String groupId;
	/** ユーザパスワード **/
	@Column(length=16)
	private String userPassword;
	/** ユーザ名 **/
	@Column(length=64)
	private String userName;
	/** 性別 **/
	@Column(length=1)
	private String gender;
	/** 生年月日 **/
	@Column
	private String birthday;
	/** 学年区分 **/
	@Column(length=1)
	private String grade;
	/** 管理者フラグ **/
	@Column(length=1)
	private String adminFlg;
	/** 更新時間 **/
	@Column
	private String updateTime;
		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAdminFlg() {
		return adminFlg;
	}

	public void setAdminFlg(String adminFlg) {
		this.adminFlg = adminFlg;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

	/**
	 * DBから取得したエンティティをList型で返却する
	 * @return エンティティリスト
	 */
	public List<String> getUserInfoList() {
		List<String> userInfoList = new ArrayList<>();
		userInfoList.add(getUserId());
		userInfoList.add(getGroupId());
		userInfoList.add(getUserPassword());
		userInfoList.add(getUserName());
		userInfoList.add(getGender());
		userInfoList.add(getBirthday());
		userInfoList.add(getGrade());
		userInfoList.add(getAdminFlg());
		userInfoList.add(getUpdateTime());
		
		return userInfoList;
	}
	
}
