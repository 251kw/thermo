package com.shantery.thermo.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.shantery.thermo.util.ThermoConstants.*;


public class ThermoReplaceValue {

	// 生年月日から年齢を割り出す
	public static String calcAge(String birthday) {

		// birthday 数字８桁でもらう
		String birthdate = birthday.replace("-", "/");

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
	
	// 性別
	public static String replaceGender(String gender) {

		if(gender.equals("M")) {
			gender = "男性";
		}else if(gender.equals("F")) {
			gender = "女性";
		}
		return gender;
	}
	
	// 学年
	public static String replaceGrade(String grade) {

		switch(grade) {
		case "0":
			grade = "なし";
			break;
		case "1":
			grade = "小学１年生";
			break;
		case "2":
			grade = "小学2年生";
			break;
		case "3":
			grade = "小学3年生";
			break;
		case "4":
			grade = "小学4年生";
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
	
	// 管理者フラグ
	public static String replaceAdmin(String admin_flg) {

		if(admin_flg.equals("1")) {
			admin_flg = "あり";
		}else if(admin_flg.equals("0")) {
			admin_flg = "なし";
		}
		return admin_flg;
	}
	
	// 空白チェック
	public static String trimBlank(String param) {
		
		// 文字列中に全角スペースがあれば全て半角スペースに置き換える
		param = param.replaceAll(FULL_SPACE, HALF_SPACE);
		// 文字列前後の半角スペースを削除
		param = param.trim();
		// 文字列中に出現する半角スペースを全角スペースに置換する
		param = param.replaceAll(HALF_SPACE, FULL_SPACE);
		
		return param;
	}
	
	// セレクトボックス生成メソッド
	// 引数はselectのname属性　
	public static String makeSelect(String division){
		
		String keys[];
		Map<String, String> map = new LinkedHashMap<>();
		
		String start = "<select name='" + division + "'" + ">";
		String end = "</select>";
		String option = "";
		
		// とりあえず新規登録ボタンの場合
		if(division.equals("regist")){
			
			keys = new String[3];
			keys[0] = "グループ";
			keys[1] = "ユーザー（個人）";
			keys[2] = "ユーザー（複数）";
			
	        map.put("group", keys[0]);
	        map.put("user", keys[1]);
	        map.put("multiuser", keys[2]);
		}
		
		for(Map.Entry<String, String> value : map.entrySet()) {
			
			option += "<option value='" + value.getKey() + "'>" + value.getValue() +"</option>";
		}
		
		return start + option + end;
	}
}
