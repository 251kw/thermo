package com.shantery.thermo.search;

import javax.persistence.Entity;

//import java.io.Serializable;

//import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Table;

@Entity
public class SearchEntity{

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private String regist_date;	//計測日
	private String user_name;	//ユーザ名
	private String gender;			//性別
	private String grade;			//学年
	private String age;				//年齢
	
	
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
	
}
