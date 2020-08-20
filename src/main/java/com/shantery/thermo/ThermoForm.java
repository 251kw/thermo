package com.shantery.thermo;

import com.shantery.thermo.util.LoginIdValid;
import com.shantery.thermo.util.LoginPassValid;

/**
 * @author k.takahashi
 * ログイン画面で入力されたパラメータを取得するためのクラス
 */
public class ThermoForm {

	/** ユーザID **/
	@LoginIdValid
	private String userId;
    
	/** ユーザパスワード **/
	@LoginPassValid
	private String userpass;

    // getter・setter
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserpass() {
		return userpass;
	}

	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	
}
