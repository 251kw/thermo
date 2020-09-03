package com.shantery.thermo.thermoInput;


import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.Size;




/**
 * 入力情報を持つクラス
 * @author t.kurihara
 *
 */
public class ThermoInputForm {
	
	@Valid
	private ArrayList<Detail> tList;
	
	public ArrayList<Detail> gettList() {
		return tList;
	}

	public void settList(ArrayList<Detail> tList) {
		this.tList = tList;
	}

	public static class Detail {
	
		
		/* ユーザー名 */
		private String userName;
		/* 性別 */
		private String gender;
		/* 学年 */
		private String grade;
		/* 年齢 */
		private String age;
		/* ユーザーID */
		private String userId;
		/* 体温 */
		private String temperature;
		/* 味覚障害 */
		private String taste;
		/* 嗅覚障害 */
		private String smell;
		/* 咳 */
		private String cough;
		/* その他 */
		@Size(max = 128)
		private String writing;
		/* 登録日 */
		private String date;
		/* 赤文字フラグ */
		private String thermoColor;
		
		
		
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
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		public String getTaste() {
			return taste;
		}
		public void setTaste(String taste) {
			this.taste = taste;
		}
		public String getSmell() {
			return smell;
		}
		public void setSmell(String smell) {
			this.smell = smell;
		}
		public String getCough() {
			return cough;
		}
		public void setCough(String cough) {
			this.cough = cough;
		}
		public String getWriting() {
			return writing;
		}
		public void setWriting(String writing) {
			this.writing = writing;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getThermoColor() {
			return thermoColor;
		}
		public void setThermoColor(String thermoColor) {
			this.thermoColor = thermoColor;
		}
	
	}
	
}
