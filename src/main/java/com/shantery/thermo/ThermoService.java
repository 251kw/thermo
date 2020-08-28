package com.shantery.thermo;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;

import com.shantery.thermo.entity.UserInfoEntity;


/**
 * @author k.takahashi
 * ログイン画面での処理で使うメソッドをまとめたクラス
 */
@Service
public class ThermoService {

	@Autowired
	ThermoRepository repository;
	@Autowired
	protected MessageSource msgPro;
	
	/**
	 * Optional型のデータをUserInfoEntity型のオブジェクトに入れなおすメソッド
	 * @param userinfo データを移し替えるオブジェクト
	 * @param data 入力されたuserIDを元に検索した結果を保持しているOptional型のオブジェクト
	 * @return データをもらったUserInfoEntity型のオブジェクト
	 */
	public UserInfoEntity checkdata(UserInfoEntity userinfo,Optional<UserInfoEntity> data) {
		
		if(data.isPresent()==true) {	// dataがデータを持っている場合
			// 値を取得するためにEntityに格納しなおす
			userinfo = data.get();
		}else {		// dataがデータを持っていない場合
			userinfo = null;
		}
		return userinfo;
	}
	
	/**
	 * ログイン画面で入力されたパスワードが正しいかどうか判別するメソッド
	 * @param parampass ログイン画面で入力されたパスワード
	 * @param datapass 入力されたuserIDを元に特定したデータベース上のパスワード
	 * @param check 正常なパスワードならtrue、異常ならfalse
	 * @return check
	 */
	public Boolean checkUser(String parampass,String datapass,Boolean check) {
	
		if(parampass.equals(datapass)) {	// パスワードが一致した場合
			check = true;
		}else {		// パスワードが不一致の場合
			check = false;	
		}
		return check;
	}

	/**
	 * 正常なユーザーか否かによってページの遷移先を指定
	 * @param check 正常なパスワードならtrue、異常ならfalse
	 * @param logintransition 遷移先を格納する変数
	 * @return logintransition
	 */
	public String setLoginTransition(Boolean check,String logintransition) {
		if(check == false) {	// 不適正ユーザーの場合
			logintransition = TO_TOP;
		}else if(check == true) {
			logintransition = TO_SEARCH;// 適正ユーザーの場合
		}
		return logintransition;
	}
	
	/**
	 * エラーメッセージを取得するメソッド
	 * @param message エラーメッセージを格納する変数
	 * @param check 正常なパスワードならtrue、異常ならfalse
	 * @return message
	 */
	public String setErrormessage(String message) {
		
		// エラーメッセージを代入
		message = msgPro.getMessage("view.errLoginUser", new String[] {}, Locale.JAPAN);
			
		return message;
	}
	
}
