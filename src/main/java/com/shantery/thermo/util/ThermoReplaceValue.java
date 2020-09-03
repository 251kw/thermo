package com.shantery.thermo.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static com.shantery.thermo.util.ThermoConstants.*;


/**
 * @author k.takahashi
 * 年齢計算、区分値から区分名への変換、空白の調整、セレクトボックスの生成
 */

public class ThermoReplaceValue {

	/**
	 * 生年月日から年齢を割り出すメソッド
	 * @param birthday 生年月日
	 * @return 年齢
	 */
	public static String calcAge(String birthday) {

		// birthday 数字８桁でもらう
		String birthdate = birthday.replace(HYPHEN, SLASH);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);

		// 生年月日を表す文字列から、LocalDateを生成
		LocalDate localBirdhdate = LocalDate.parse(birthdate, formatter);

		// 現在の日付を取得
		LocalDate nowDate = LocalDate.now();

		// 現在と生年月日の差分を年単位で算出することによって、年齢を計算する
		long age = ChronoUnit.YEARS.between(localBirdhdate, nowDate);

		String str = String.valueOf(age);

		return str;
	}
	
	/**
	 * 区分の種類と区分値から区分名を取得するメソッド
	 * @param kbn_type 区分の種類
	 * @param kbn_value DBに登録されている区分値
	 * @return 区分名
	 */
	public static String valueToName(String kbn_type, String kbn_value){

		// return用のString
		String kbn_name = null;
		
		// 管理者
		if(kbn_type.equals(KBN_TYPE_ADMIN)) {
			
			switch (kbn_value) {
			
			case KBN_VALUE_ADMIN_ON:
				kbn_name = KBN_NAME_ADMIN_ON;
				break;
				
			case KBN_VALUE_ADMIN_OFF:
				kbn_name = KBN_NAME_ADMIN_OFF;
				break;
			}
		// 性別
		}else if(kbn_type.equals(KBN_TYPE_GENDER)) {
			
			switch (kbn_value) {
			
			case KBN_VALUE_GENDER_MALE:
				kbn_name = KBN_NAME_GENDER_MALE;
				break;
				
			case KBN_VALUE_GENDER_FEMALE:
				kbn_name = KBN_NAME_GENDER_FEMALE;
				break;
			}
		// 学年
		}else if(kbn_type.equals(KBN_TYPE_GRADE)) {
			
			switch(kbn_value) {
			
			case KBN_VALUE_GRADE_ZERO:
				kbn_name = KBN_NAME_GRADE_ZERO;
				break;
			case KBN_VALUE_GRADE_ONE:
				kbn_name = KBN_NAME_GRADE_ONE;
				break;
			case KBN_VALUE_GRADE_TWO:
				kbn_name = KBN_NAME_GRADE_TWO;
				break;
			case KBN_VALUE_GRADE_THREE:
				kbn_name = KBN_NAME_GRADE_THREE;
				break;
			case KBN_VALUE_GRADE_FOUR:
				kbn_name = KBN_NAME_GRADE_FOUR;
				break;
			case KBN_VALUE_GRADE_FIVE:
				kbn_name = KBN_NAME_GRADE_FIVE;
				break;
			case KBN_VALUE_GRADE_SIX:
				kbn_name = KBN_NAME_GRADE_SIX;
				break;
			case KBN_VALUE_GRADE_A:
				kbn_name = KBN_NAME_GRADE_A;
				break;
			case KBN_VALUE_GRADE_B:
				kbn_name = KBN_NAME_GRADE_B;
				break;
			case KBN_VALUE_GRADE_C:
				kbn_name = KBN_NAME_GRADE_C;
				break;
			}
		}else if(kbn_type.equals(KBN_TYPE_EXISTENCE)) {
			
			switch(kbn_value) {
			
			case KBN_VALUE_WITH:
				kbn_name = KBN_NAME_WITH;
				break;
				
			case KBN_VALUE_WITHOUT:
				kbn_name = KBN_NAME_WITHOUT;
				break;
			}
		}
		// 区分名を返す
		return kbn_name;
	}
	
	/**
	 * 文字列の前後の空白を取り除く& 文字列中の半角スペースを全角スぺースに置換する
	 * @param param
	 * @return
	 */
	public static String trimBlank(String param) {
		
		// 文字列中に全角スペースがあれば全て半角スペースに置き換える
		param = param.replaceAll(FULL_SPACE, HALF_SPACE);
		// 文字列前後の半角スペースを削除
		param = param.trim();
		// 文字列中に出現する半角スペースを全角スペースに置換する
		param = param.replaceAll(HALF_SPACE, FULL_SPACE);
		
		return param;
	}
	
}
