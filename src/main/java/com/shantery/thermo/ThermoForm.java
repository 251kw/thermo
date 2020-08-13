package com.shantery.thermo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import static com.shantery.thermo.util.ThermoConstants.*;

/**
 * @author k.takahashi
 * ログイン画面で入力されたパラメータを取得するためのクラス
 */
public class ThermoForm {

	/** ユーザID **/
    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp=THERMO_REGEX_PATTERN)
	private String userId;
    
	/** ユーザパスワード **/
    @NotBlank
    @Size(min = 4, max = 16)
    @Pattern(regexp=THERMO_REGEX_PATTERN)
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
