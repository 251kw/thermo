package com.shantery.thermo.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class ThermoReplaceValue {

	public static String calcAge(String birthday) {

		// birthday 数字８桁でもらう
		birthday.replace("-", "/");

		String birthdate = birthday;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		// 生年月日を表す文字列から、LocalDateを生成
		LocalDate localBirdhdate = LocalDate.parse(birthdate, formatter);

		// 現在の日付を取得
		LocalDate nowDate = LocalDate.now();

		// 現在と生年月日の差分を年単位で算出することによって、年齢を計算する
		long age = ChronoUnit.YEARS.between(localBirdhdate, nowDate);

		String str = String.valueOf(age);

		return str;
	}
	
	public static String replaceGender(String gender) {

		if(gender.equals("M")) {
			gender = "男性";
		}else if(gender.equals("F")) {
			gender = "女性";
		}
		return gender;
	}
	
	public static String replaceGrade(String grade) {

		switch(grade) {
		case "1":
			grade = "少学１年生";
			break;
		case "2":
			grade = "少学2年生";
			break;
		case "3":
			grade = "少学3年生";
			break;
		case "4":
			grade = "少学4年生";
			break;
		case "5":
			grade = "少学5年生";
			break;
		case "6":
			grade = "少学6年生";
			break;
		case "A":
			grade = "中学１年生";
			break;
		case "B":
			grade = "中学2年生";
			break;
		case "C":
			grade = "中学3年生";
			break;
		}
		return grade;
	}
	
	public static String replaceAdmin(String admin_flg) {

		if(admin_flg.equals("1")) {
			admin_flg = "あり";
		}else if(admin_flg.equals("0")) {
			admin_flg = "なし";
		}
		return admin_flg;
	}
}
