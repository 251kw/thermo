package com.shantery.thermo;

import static com.shantery.thermo.util.ThermoConstants.TO_TOP;
import static com.shantery.thermo.util.ThermoConstants.USERS_MULTI_SET;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;


/**
 * @author k.takahashi
 * ログイン画面での処理で使うメソッドをまとめたクラス
 */
@Service
public class ThermoService {

	@Autowired
	ThermoRepository repository;
	
	/**
	 * Optional型のデータをUserInfoEntity型のオブジェクトに入れなおすメソッド
	 * @param userinfo データを移し替えるオブジェクト
	 * @param data 入力されたuserIDを元に検索した結果を保持しているOptional型のオブジェクト
	 * @return データをもらったUserInfoEntity型のオブジェクト
	 */
	public ThermoInfoEntity checkdata(ThermoInfoEntity thermoinfo,Optional<ThermoInfoEntity> data) {
		
		if(data.isPresent()==true) {	// dataがデータを持っている場合
			// 値を取得するためにEntityに格納しなおす
			thermoinfo = data.get();
		}else {		// dataがデータを持っていない場合
			thermoinfo = null;
		}
		return thermoinfo;
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
			logintransition = "search";// 適正ユーザーの場合
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
		message = "ログインIDまたはパスワードが間違っています";
			
		return message;
	}
	
	/**
	 * 新規登録の種類によって遷移先を振り分けるメソッド
	 * @param regist ログイン画面で選択された登録の種類
	 * @param registtransition 遷移先を格納する変数 
	 * @return registtransition
	 */
	// TODO 未使用のメソッド
	public String setRegistTransition(String regist,String registtransition) {
		if(regist.equals("group")) {	// グループが選択されていた場合
			registtransition = "groupInfoInput";
		}else if(regist.equals("user")){	// ユーザー（個人）が選択されていた場合
			registtransition = "userInfoInput";
		}else if(regist.equals("multiuser")) {	// ユーザー（複数）が選択されていた場合
			registtransition = USERS_MULTI_SET;
		}
		return registtransition;
	}
	
}
