package com.shantery.thermo.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsbnValidator implements ConstraintValidator<IsbnValid, String>{
	
	/**
	 * isValidを呼ぶための初期化処理
	 */
	@Override
	public void initialize(IsbnValid isbn) {
    }
	
	/**
	 * 生年月日の入力形式をチェックする
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {  //nullが来ていたら
			return true;
		}
		//正規表現を固定
		Pattern ptn = Pattern.compile("^(\\d{4})[-/](\\d{2})[-/](\\d{2})$");
		//正規表現のチェック
		Matcher mch = ptn.matcher(value);
		if (mch.find()) { //マッチしていたら
			try {
				LocalDate.of(Integer.valueOf(mch.group(1)), Integer.valueOf(mch.group(2)),
						Integer.valueOf(mch.group(3))); //生年月日の入力値が正しければエラーは起きない
			} catch (Exception e) { //不正な値を入力していたら(15月44日など)
				return false;
			}
		} else { //正規表現にマッチしない場合
			return false;
		}

		return true;
	}

}
