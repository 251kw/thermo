package com.shantery.thermo.util;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

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
	
	/**
	 * htmlに直接セレクトボックスを出力するメソッド
	 * @param division selectのname属性、またはid属性
	 * @return セレクトボックスを生成するString
	 */
	/** 使い方：<span th:utext="${T(com.shantery.thermo.util.ThermoReplaceValue).makeSelect('引数')}"></span> **/
	public static String makeSelect(String division){

		// 配列とマップを定義
		String keys[];
		Map<String, String> map = new LinkedHashMap<>();
		
		// htmlを構成するパーツ
		String start = SELECT_HTML_PARTS_A1 + division + SELECT_HTML_PARTS_B + SELECT_HTML_PARTS_C;
		String startWithField = SELECT_HTML_PARTS_A2 + SELECT_HTML_PARTS_B + SELECT_HTML_PARTS_C;
		String startWithId = SELECT_HTML_PARTS_A1 + division + SELECT_HTML_PARTS_B + SELECT_HTML_PARTS_D + SELECT_HTML_PARTS_E + division + SELECT_HTML_PARTS_B + SELECT_HTML_PARTS_C;
		String end = SELECT_HTML_PARTS_F;
		String option = EMPTY;
		String comp = null;
		
		// 区分の種類によって処理を分岐
		switch(division) {
		
		// 新規登録
		case SELECT_TYPE_REGIST:
				
			keys = new String[3];
			keys[0] = SELECT_REGIST_VALUE_GROUP;
			keys[1] = SELECT_REGIST_VALUE_USER;
			keys[2] = SELECT_REGIST_VALUE_MULTIUSER;
				
		    map.put(SELECT_REGIST_KEY_GROUP, keys[0]);
		    map.put(SELECT_REGIST_KEY_USER, keys[1]);
		    map.put(SELECT_REGIST_KEY_MULTIUSER, keys[2]);
		    
		    break;
		    
		// 性別
		case SELECT_TYPE_GENDER:
			
			keys = new String[2];
			keys[0] = SELECT_GENDER_VALUE_MALE;
			keys[1] = SELECT_GENDER_VALUE_FEMALE;
				
		    map.put(SELECT_GENDER_KEY_MALE, keys[0]);
		    map.put(SELECT_GENDER_KEY_FEMALE, keys[1]);
		    
		    break;
		    
		 // 管理者フラグ
		 case SELECT_TYPE_ADMINFLG:
		 			
 			keys = new String[2];
 			keys[0] = SELECT_ADMINFLG_VALUE_NORMAL;
 			keys[1] = SELECT_ADMINFLG_VALUE_ADMIN;
 				
 		    map.put(SELECT_ADMINFLG_KEY_NORMAL, keys[0]);
 		    map.put(SELECT_ADMINFLG_KEY_ADMIN, keys[1]);
 		    
 		    break;
		    
		// 学年
		case SELECT_TYPE_GRADE:
			
			keys = new String[10];
			keys[0] = SELECT_GRADE_VALUE_ZERO;
			keys[1] = SELECT_GRADE_VALUE_ONE;
			keys[2] = SELECT_GRADE_VALUE_TWO;
			keys[3] = SELECT_GRADE_VALUE_THREE;
			keys[4] = SELECT_GRADE_VALUE_FOUR;
			keys[5] = SELECT_GRADE_VALUE_FIVE;
			keys[6] = SELECT_GRADE_VALUE_SIX;
			keys[7] = SELECT_GRADE_VALUE_A;
			keys[8] = SELECT_GRADE_VALUE_B;
			keys[9] = SELECT_GRADE_VALUE_C;
				
		    map.put(SELECT_GRADE_KEY_ZERO, keys[0]);
		    map.put(SELECT_GRADE_KEY_ONE, keys[1]);
		    map.put(SELECT_GRADE_KEY_TWO, keys[2]);
		    map.put(SELECT_GRADE_KEY_THREE, keys[3]);
		    map.put(SELECT_GRADE_KEY_FOUR, keys[4]);
		    map.put(SELECT_GRADE_KEY_FIVE, keys[5]);
		    map.put(SELECT_GRADE_KEY_SIX, keys[6]);
		    map.put(SELECT_GRADE_KEY_A, keys[7]);
		    map.put(SELECT_GRADE_KEY_B, keys[8]);
		    map.put(SELECT_GRADE_KEY_C, keys[9]);
		    
		    break;
		    
		// 学年と空欄  
		case SELECT_TYPE_GRADE_AND_BLANK:
			
			keys = new String[11];
			keys[0] = EMPTY;
			keys[1] = SELECT_GRADE_VALUE_ZERO;
			keys[2] = SELECT_GRADE_VALUE_ONE;
			keys[3] = SELECT_GRADE_VALUE_TWO;
			keys[4] = SELECT_GRADE_VALUE_THREE;
			keys[5] = SELECT_GRADE_VALUE_FOUR;
			keys[6] = SELECT_GRADE_VALUE_FIVE;
			keys[7] = SELECT_GRADE_VALUE_SIX;
			keys[8] = SELECT_GRADE_VALUE_A;
			keys[9] = SELECT_GRADE_VALUE_B;
			keys[10] = SELECT_GRADE_VALUE_C;
				
			map.put(EMPTY, EMPTY);
		    map.put(SELECT_GRADE_KEY_ZERO, keys[1]);
		    map.put(SELECT_GRADE_KEY_ONE, keys[2]);
		    map.put(SELECT_GRADE_KEY_TWO, keys[3]);
		    map.put(SELECT_GRADE_KEY_THREE, keys[4]);
		    map.put(SELECT_GRADE_KEY_FOUR, keys[5]);
		    map.put(SELECT_GRADE_KEY_FIVE, keys[6]);
		    map.put(SELECT_GRADE_KEY_SIX, keys[7]);
		    map.put(SELECT_GRADE_KEY_A, keys[8]);
		    map.put(SELECT_GRADE_KEY_B, keys[9]);
		    map.put(SELECT_GRADE_KEY_C, keys[10]);
		    
		    break;
		}
			
		for(Map.Entry<String, String> value : map.entrySet()) {
				
			option += SELECT_HTML_PARTS_G + value.getKey() + SELECT_HTML_PARTS_H + value.getValue() + SELECT_HTML_PARTS_I;
		}
			
		if(division.equals(SELECT_TYPE_REGIST)) {
			comp = startWithId + option + end;
		}else if(division.equals(SELECT_TYPE_GRADE_AND_BLANK)) {
			comp = startWithField + option + end;
		}else {
			comp = start + option + end;
		}
		
		return comp;
	}
}
