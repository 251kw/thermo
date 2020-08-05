package com.shantery.thermo.userInfoMulti;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserInfoMultiBeanValidation {

	/** グループID **/
	@NotBlank(message = "gId")
	@Size(min = 4, max = 32,message="gId")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="gId")
	private String group_id;
	/** グループパスワード **/
	@NotBlank(message = "gpass")
	@Size(min = 4, max = 16,message="gPass")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="gPass")
	private String group_pass;
	/** ユーザID **/
	@NotBlank(message = "uId")
	@Size(min = 4, max = 32,message="uId")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="uId")
	private String user_id;
	/** ユーザパスワード **/
	@NotBlank(message = "uPass")
	@Size(min = 4, max = 16,message="uPass")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="uPass")
	private String user_pass;
	/** ユーザ名 **/
	@NotBlank(message = "uName")
	@Size(min = 1, max = 64,message="uName")
	//@Pattern(regexp = "[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠]+$",message="uName")
	private String user_name;
	/** 性別 **/
	@NotBlank(message = "gender")
	@Size(min = 1,max = 1,message="gender")
	@Pattern(regexp = "[MF]",message="gender")
	private String gender;
	/** 生年月日 **/
	@NotBlank(message = "birthday")
	private String birthday;
	/** 学年区分 **/
	@NotBlank(message = "grade")
	@Size(min = 1,max = 1,message="grade")
	private String grade;
	/** 管理者フラグ **/
	@NotBlank(message = "flg")
	@Size(min = 1,max = 1,message="flg")
	private String admin_flg;
	
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getGroup_pass() {
		return group_pass;
	}
	public void setGroup_pass(String group_pass) {
		this.group_pass = group_pass;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	
	public void setAll(String[] info){
		this.group_id = info[0];
		this.group_pass = info[1];
		this.user_id = info[2];
		this.user_pass = info[3];
		this.user_name = info[4];
		this.gender = info[5];
		this.birthday = info[6];
		this.grade = info[7];
		this.admin_flg = info[8];
	}
	
}
