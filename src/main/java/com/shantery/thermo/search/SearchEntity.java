package com.shantery.thermo.search;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.poi.hpsf.Decimal;

@Entity
public class SearchEntity{

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private String user_id;			//ユーザID
	private String regist_date;	//計測日
	private String user_name;	//ユーザ名
	private String gender;			//性別
	private String grade;			//学年
	private String age;				//年齢
	private String thermo;			//体温
	private String taste_disorder;		//味覚障害
	private String olfactory_disorder;		//嗅覚障害
	private String cough;			//咳
	private String other;           //その他
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRegist_date() {
		return regist_date;
	}
	public void setRegist_date(String regist_date) {
		this.regist_date = regist_date;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getThermo() {
		return thermo;
	}
	public void setThermo(String thermo) {
		this.thermo = thermo;
	}
	public String getTaste_disorder() {
		return taste_disorder;
	}
	public void setTaste_disorder(String taste_disorder) {
		this.taste_disorder = taste_disorder;
	}
	public String getOlfactory_disorder() {
		return olfactory_disorder;
	}
	public void setOlfactory_disorder(String olfactory_disorder) {
		this.olfactory_disorder = olfactory_disorder;
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
	
}
