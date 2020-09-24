package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Id;

import com.shantery.thermo.userInfo.UserInfoForm;

/**
 * @author k.takahashi
 * editUserInfoMultiパッケージ内で使うform
 */
public class EditUserInfoMultiForm {


	private ArrayList<contents> userList;
	
	public ArrayList<contents> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<contents> userList) {
		this.userList = userList;
	}
	
	private String[] inputMultiCheck;
	
	public String[] getInputMultiCheck() {
		return inputMultiCheck;
	}
	
	public void setInputMultiCheck(String[] inputMultiCheck) {
		this.inputMultiCheck = inputMultiCheck;
	}

	public static class contents {
		
			
		@Id
		/** ユーザID **/
		@Column(length=32,name="user_id")
		private String userId;
		/** ユーザパスワード **/
		@Column(length=16,name="user_pass")
		private String userPass;
		/** 氏名 **/
		@Column(length=64,name="user_name")
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
		@Column(length=1,name="admin_flg")
		private String adminFlg;
		//private String multiChecks;
		
		
		public String getUser_id() {
			return userId;
		}
		public void setUser_id(String userId) {
			this.userId = userId;
		}
		public String getUser_pass() {
			return userPass;
		}
		public void setUser_pass(String userPass) {
			this.userPass = userPass;
		}
		public String getUser_name() {
			return userName;
		}
		public void setUser_name(String userName) {
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
			return adminFlg;
		}
		public void setAdmin_flg(String adminFlg) {
			this.adminFlg = adminFlg;
		}
		/*
		public String getMultiChecks() {
			return multiChecks;
		}
		public void setMultiChecks(String multiChecks) {
			this.multiChecks = multiChecks;
		}
		*/
	}
}
