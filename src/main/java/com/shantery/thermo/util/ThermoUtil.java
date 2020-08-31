package com.shantery.thermo.util;

/**
 * @author y.okino
 *Thermoシステムで使用する共通クラス
 *Thermo固有の共通した処理はこのクラスに集約
 */
public class ThermoUtil {

	/** インスタンス生成禁止 **/
	private ThermoUtil() {
	}

	/**
	 * ファイル名から拡張子を返します。
	 * @param fileName ファイル名
	 * @return ファイルの拡張子
	 */
	public static String getSuffix(String fileName) {
	    if (fileName == null) {
	        return null;
	    }
	    int point = fileName.lastIndexOf(".");
	    if (point != -1) {
	    	String rtn =  fileName.substring(point + 1);
	        return rtn;
	    }
	    return fileName;
	}

	/**
	 * 生年月日入力時、年齢制限を行うメソッド
	 * @param birthday 生年月日
	 * @return true/false 年齢が0歳～120歳までの間か否か
	 *
	 */
	public static boolean ageLimit(String birthday) {
		int Age = Integer.parseInt(ThermoReplaceValue.calcAge(birthday));//生年月日から年齢を算出
		if(0 <= Age && Age <= 120) {
			return true;
		}else {
			return false;
		}
	}

}
