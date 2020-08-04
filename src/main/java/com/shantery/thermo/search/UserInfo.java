//package com.shantery.thermo.search;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
///**
// * user_infoデータベースのエンティティクラス
// */
//@Entity
//@Table(name = "user_info") 
//public class UserInfo implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//	
//	@OneToMany(mappedBy = "user_info", cascade = CascadeType.ALL)
//	@Column(nullable = true)
//	private List<ThermoInfo> thermo;
//	
//	public List<ThermoInfo> getThermo() {
//		return thermo;
//	}
//
//	public void setThermo(List<ThermoInfo> thermo) {
//		this.thermo = thermo;
//	}
//
////	private String age;
////	
////	public String getAge() {
////		return age;
////	}
////
////	public void setAge(String age) {
////		this.age = age;
////	}
//
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	/** ユーザID **/
//	@Column(length=32)
//	private String user_id;
//	/** グループID **/
//	@Column(length=32, name="group_id")
//	private String groupId;
//	/** ユーザパスワード **/
//	@Column(length=16)
//	private String user_pass;
//	/** ユーザ名 **/
//	@Column(length=64)
//	private String user_name;
//	/** 性別 **/
//	@Column(length=1)
//	private String gender;
//	/** 生年月日 **/
//	@Column
//	private String birthday;
//	/** 学年区分 **/
//	@Column(length=1)
//	private String grade;
//	/** 管理者フラグ **/
//	@Column(length=1)
//	private String admin_flg;
//	/** 更新時間 **/
//	@Column
//	private String update_time;
//	
//	
//	public String getUser_id() {
//		return user_id;
//	}
//
//
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
//
//
//	public String getGroup_id() {
//		return groupId;
//	}
//
//
//	public void setGroup_id(String group_id) {
//		this.groupId = group_id;
//	}
//
//
//	public String getUser_pass() {
//		return user_pass;
//	}
//
//
//	public void setUser_pass(String user_pass) {
//		this.user_pass = user_pass;
//	}
//
//
//	public String getUser_name() {
//		return user_name;
//	}
//
//
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
//
//
//	public String getGender() {
//		return gender;
//	}
//
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//
//	public String getBirthday() {
//		return birthday;
//	}
//
//
//	public void setBirthday(String birthday) {
//		this.birthday = birthday;
//	}
//
//
//	public String getGrade() {
//		return grade;
//	}
//
//
//	public void setGrade(String grade) {
//		this.grade = grade;
//	}
//
//
//	public String getAdmin_flg() {
//		return admin_flg;
//	}
//
//
//	public void setAdmin_flg(String admin_flg) {
//		this.admin_flg = admin_flg;
//	}
//
//
//	public String getUpdate_time() {
//		return update_time;
//	}
//
//
//	public void setUpdate_time(String update_time) {
//		this.update_time = update_time;
//	}
//
//
//	public List<String> getUserInfoList() {
//		List<String> userInfoList = new ArrayList<>();
//		userInfoList.add(getUser_id());
//		userInfoList.add(getGroup_id());
//		userInfoList.add(getUser_pass());
//		userInfoList.add(getUser_name());
//		userInfoList.add(getGender());
//		userInfoList.add(getBirthday());
//		userInfoList.add(getGrade());
//		userInfoList.add(getAdmin_flg());
//		userInfoList.add(getUpdate_time());
//		
//		return userInfoList;
//	}
//	
//}
