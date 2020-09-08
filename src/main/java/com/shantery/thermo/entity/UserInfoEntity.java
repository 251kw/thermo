package com.shantery.thermo.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.shantery.thermo.userInfo.UserInfoForm;

/**
 * user_infoデータベースのエンティティクラス
 */
@Entity
@Table(name = "user_info") 
public class UserInfoEntity {

	@Id
	/** ユーザID **/
	@Column(length=32,name="user_id")
	private String userId;
	/** グループID **/
	@Column(length=32,name="group_id")
	private String groupId;
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
	/** 更新時間 **/
	@Column(name="update_time")
	private String updateTime;
	
	
	/** ThermoInfoEntity **/
	@OneToMany					//↓SQLのINSERT文,UPDATE文に含むかどうか指定
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private List<ThermoInfoEntity> thermoInfoEntity;
	
	public List<ThermoInfoEntity> getThermoInfoEntity() {
		return thermoInfoEntity;
	}

	public void setThermoInfoEntity(List<ThermoInfoEntity> thermoInfoEntity) {
		this.thermoInfoEntity = thermoInfoEntity;
	}

	/** GroupMstEntity **/
	@ManyToOne				    //↓SQLのINSERT文,UPDATE文に含むかどうか指定
	@JoinColumn(name="group_id", insertable=false, updatable=false)
	private GroupMstEntity groupMstEntity;

	public GroupMstEntity getUGroupMstEntity() {
		return groupMstEntity;
	}

	public void setGroupMstEntity(GroupMstEntity groupMstEntity) {
		this.groupMstEntity = groupMstEntity;
	}

	public String getUser_id() {
		return userId;
	}


	public void setUser_id(String user_id) {
		this.userId = user_id;
	}


	public String getGroup_id() {
		return groupId;
	}


	public void setGroup_id(String group_id) {
		this.groupId = group_id;
	}


	public String getUser_pass() {
		return userPass;
	}


	public void setUser_pass(String user_pass) {
		this.userPass = user_pass;
	}


	public String getUser_name() {
		return userName;
	}


	public void setUser_name(String user_name) {
		this.userName = user_name;
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


	public void setAdmin_flg(String admin_flg) {
		this.adminFlg = admin_flg;
	}


	public String getUpdate_time() {
		return updateTime;
	}


	public void setUpdate_time(String update_time) {
		this.updateTime = update_time;
	}


	/**
	 * DBから取得したエンティティをList型で返却
	 * @return エンティティリスト
	 */
	public List<String> getUserInfoList() {
		List<String> userInfoList = new ArrayList<>();
		userInfoList.add(getUser_id());
		userInfoList.add(getGroup_id());
		userInfoList.add(getUser_pass());
		userInfoList.add(getUser_name());
		userInfoList.add(getGender());
		userInfoList.add(getBirthday());
		userInfoList.add(getGrade());
		userInfoList.add(getAdmin_flg());
		userInfoList.add(getUpdate_time());
		
		return userInfoList;
	}
	
	public String[] getHead() {
		String[] head = {"a"};
	
	return head;
	
	}
	
	/**
	 * 一括登録された情報をセットする
	 * @param userInfo ユーザー情報
	 */
	public void setUserInfo(String[] userInfo) {
		
		this.userId = userInfo[2];
		this.groupId = userInfo[0];
		this.userPass = userInfo[3];
		this.userName = userInfo[4];
		this.gender = userInfo[5];
		this.birthday = userInfo[6];
		this.grade = userInfo[7];
		this.adminFlg = userInfo[8];
		//現在日時の取得と日付の書式設定
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.updateTime = sdf.format(calendar.getTime());
		
	}
	
	/**
	 * Entityで受け取った情報をFormに変換する
	 * @author h.komatsu
	 * @return uInFm ユーザー情報
	 */
	public UserInfoForm _toConvertUserInfoForm(){
		UserInfoForm uInFm = new UserInfoForm();
		
		uInFm.setGroupId(getGroup_id());
		uInFm.setUserId(getUser_id());
		uInFm.setUserPass(getUser_pass());
		uInFm.setUserName(getUser_name());
		uInFm.setGender(getGender());
		uInFm.setBirthday(getBirthday());
		uInFm.setGrade(getGrade());
		uInFm.setAdminFlg(getAdmin_flg());
		uInFm.setUpdateTime(getUpdate_time());
	    return uInFm;
	 }
	
}
