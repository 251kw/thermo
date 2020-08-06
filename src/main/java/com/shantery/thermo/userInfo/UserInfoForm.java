package com.shantery.thermo.userInfo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;


/**
 * Fromクラス
 */

public class UserInfoForm implements Serializable {

	
	/** グループID **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 32,message="4文字以上、32文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String groupId;
	/** グループパスワード **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 16,message="4文字以上、16文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String groupPass;
	/** ユーザID **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 32,message="4文字以上、32文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String userId;
	/** ユーザーパスワード **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 16,message="4文字以上、16文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String userPass;
	/** 氏名 **/
	@NotBlank(message = "※入力必須項目※　記号以外で入力してください")
	@Size(max = 64,message="64文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠]+$",message="記号、スペースは入力できません")//TODO
	private String userName;
	/** 性別 **/
	@NotBlank
	private String gender;
	/** 生年月日 **/
	@NotBlank
	private String birthday;
	/** 学年区分 **/
	@NotBlank(message = "※入力必須項目※")
	private String grade;
	/** 管理者フラグ **/
	@NotBlank
	private String adminFlg;
	/** 更新時間 **/
	private String updateTime;
	

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

	public String getGroupPass() {
		return groupPass;
	}

	public void setGroupPass(String groupPass) {
		this.groupPass = groupPass;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
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
	
	/*
	//Formで受け取った情報をEntityに変換する
	public ThermoInfoEntity _toConvertUserInfoEntity(){
		ThermoInfoEntity uInEn = new ThermoInfoEntity();
		
		uInEn.setGroup_id(getGroupId());
		uInEn.setUser_id(getUserId());
		uInEn.setUser_pass(getUserPass());
		uInEn.setUser_name(getUserName());
		uInEn.setGender(getGender());
		uInEn.setBirthday(getBirthday());
		uInEn.setGrade(getGrade());
		uInEn.setAdmin_flg(getAdminFlg());
		uInEn.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	    return uInEn ;
	 }
	 */

	
}
