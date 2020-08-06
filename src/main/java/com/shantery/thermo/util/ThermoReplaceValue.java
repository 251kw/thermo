package com.shantery.thermo.util;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.shantery.thermo.entity.ThermoInfoEntity;

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

	public ArrayList<ThermoInfoEntity> replaceGender(ArrayList<ThermoInfoEntity> list) {
		// m or f
		for (ThermoInfoEntity entity : list) {
			if (entity.getUserInfoEntity().getGender().equals("M")) {
				entity.getUserInfoEntity().setGender("男性");
			} else if (entity.getUserInfoEntity().getGender().equals("F")) {
				entity.getUserInfoEntity().setGender("女性");
			}
		}
		return list;
	}
}
