package com.shantery.thermo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ThermoForm {

	/** ユーザID **/
    @NotBlank(message="※スペースは入力できません")
    @Size(min = 4, max = 32, message="※４～３２文字以内でで入力してください")
    @Pattern(regexp="[a-zA-Z0-9]*",message="※半角英数字で入力してください")
	private String userId;
    
	/** ユーザパスワード **/
    @NotBlank(message="※スペースは入力できません")
    @Size(min = 4, max = 16, message="※４～３２文字以内でで入力してください")
    @Pattern(regexp="[a-zA-Z0-9]*",message="※半角英数字で入力してください")
	private String userpass;

    
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
