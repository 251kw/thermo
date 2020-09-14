package com.shantery.thermo.entity;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * thermo_infoデータベースのエンティティ
 */
@Entity
@IdClass(ThermoKey.class)
@Table(name = "thermo_info")
public class ThermoInfoEntity {

	/** ユーザID **/
	@Id
	@Column(length=32,name="user_id")
	private String userId;
	/** 体温 **/
	@Column
	private String thermo;
	/** 味覚障害 **/
	@Column(length=1,name="taste_disorder")
	private String tasteDisorder;
	/** 嗅覚障害 **/
	@Column(length=1,name="olfactory_disorder")
	private String olfactoryDisorder;
	/** 咳 **/
	@Column(length=1)
	private String cough;
	/** その他 **/
	@Column(length=128)
	private String other;
	/** 登録日 **/
	@Id
	@Column(name="regist_date")
	private String registDate;
	/** 登録者 **/
	@Column(length=32,name="update_user")
	private String updateUser;
	/** 更新日 **/
	@Column(name="update_time")
	private String updateTime;


	/** UserInfoEntity **/
	@ManyToOne				    //↓SQLのINSERT文,UPDATE文に含むかどうか指定
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private UserInfoEntity userInfoEntity;

	public UserInfoEntity getUserInfoEntity() {
		return userInfoEntity;
	}

	public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}



	public String getUser_id() {
		return userId;
	}

	public void setUser_id(String user_id) {
		this.userId = user_id;
	}

	public String getThermo() {
		return thermo;
	}

	public void setThermo(String thermo) {
		this.thermo = thermo;
	}

	public String getTaste_disorder() {
		return tasteDisorder;
	}

	public void setTaste_disorder(String taste_disorder) {
		this.tasteDisorder = taste_disorder;
	}

	public String getOlfactory_disorder() {
		return olfactoryDisorder;
	}

	public void setOlfactory_disorder(String olfactory_disorder) {
		this.olfactoryDisorder = olfactory_disorder;
	}

	public String getCough() {
		return cough;
	}

	public void setCough(String cough) {
		this.cough = cough;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getRegist_date() {
		return registDate;
	}

	public void setRegist_date(String regist_date) {
		this.registDate = regist_date;
	}

	public String getUpdate_user() {
		return updateUser;
	}

	public void setUpdate_user(String update_user) {
		this.updateUser = update_user;
	}

	public String getUpdate_time() {
		return updateTime;
	}

	public void setUpdate_time(String update_time) {
		this.updateTime = update_time;
	}

	/**
	 * DBから取得したエンティティをList型で返却する
	 * @return エンティティリスト
	 */
	public List<String> getThermoList() {
		List<String> thermoList = new ArrayList<>();
		thermoList.add(getUser_id());
		thermoList.add(getThermo());
		thermoList.add(getTaste_disorder());
		thermoList.add(getOlfactory_disorder());
		thermoList.add(getCough());
		thermoList.add(getOther());
		thermoList.add(getRegist_date());
		thermoList.add(getUpdate_user());
		thermoList.add(getUpdate_time());

		return thermoList;
	}
}
