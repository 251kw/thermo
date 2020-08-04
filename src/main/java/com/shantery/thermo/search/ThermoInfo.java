//package com.shantery.thermo.search;
//
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
////import javax.persistence.IdClass;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//
///**
// * thermo_infoデータベースのエンティティクラス
// */
//@Entity
////@IdClass(ThermoInfo.class)
//@Table(name = "thermo_info")
//public class ThermoInfo implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//	
//	@ManyToOne
//	@JoinColumn(name="user_id", insertable = false, updatable = false)
//	private UserInfo user_info;
//	
//	public ThermoInfo() {
//		super();
//		user_info = new UserInfo();
//	}
//	
//	public UserInfo getUserinfo() {
//		return user_info;
//	}
//
//	public void setUserinfo(UserInfo user_info) {
//		this.user_info = user_info;
//	}
//
//
//	/** ユーザーID **/
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(length=32)
//	private String user_id;
//	/** サーモID **/
//	@Id
//	@Column(length=32)
//	private String thermo_id;
//	/** 体温 **/
//	@Column
//	private String thermo;
//	/** 味覚障害 **/
//	@Column(length=1)
//	private String taste_disorder;
//	/** 嗅覚障害 **/
//	@Column(length=1)
//	private String olfactory_disorder;
//	/** 咳 **/
//	@Column(length=1)
//	private String cough;
//	/** その他 **/
//	@Column(length=128)
//	private String other;
//	/** 登録日 **/
//	@Column
//	private String regist_date;
//	/** 登録者 **/
//	@Column(length=32)
//	private String update_user;
//	/** 登録時間 **/
//	@Column
//	private String update_time;
//	
//	public String getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(String user_id) {
//		this.user_id = user_id;
//	}
//
//	public String getThermo_id() {
//		return thermo_id;
//	}
//
//	public void setThermo_id(String thermo_id) {
//		this.thermo_id = thermo_id;
//	}
//
//	public String getThermo() {
//		return thermo;
//	}
//
//	public void setThermo(String thermo) {
//		this.thermo = thermo;
//	}
//
//	public String getTaste_disorder() {
//		return taste_disorder;
//	}
//
//	public void setTaste_disorder(String taste_disorder) {
//		this.taste_disorder = taste_disorder;
//	}
//
//	public String getOlfactory_disorder() {
//		return olfactory_disorder;
//	}
//
//	public void setOlfactory_disorder(String olfactory_disorder) {
//		this.olfactory_disorder = olfactory_disorder;
//	}
//
//	public String getCough() {
//		return cough;
//	}
//
//	public void setCough(String cough) {
//		this.cough = cough;
//	}
//
//	public String getOther() {
//		return other;
//	}
//
//	public void setOther(String other) {
//		this.other = other;
//	}
//
//	public String getRegist_date() {
//		return regist_date;
//	}
//
//	public void setRegist_date(String regist_date) {
//		this.regist_date = regist_date;
//	}
//
//	public String getUpdate_user() {
//		return update_user;
//	}
//
//	public void setUpdate_user(String update_user) {
//		this.update_user = update_user;
//	}
//
//	public String getUpdate_time() {
//		return update_time;
//	}
//
//	public void setUpdate_time(String update_time) {
//		this.update_time = update_time;
//	}
//		
//}