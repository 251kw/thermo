package com.shantery.thermo.editUserInfoMulti;

import static com.shantery.thermo.util.ThermoConstants.*;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.ThermoForm;

/**
 * @author d.ito
 *ログインユーザーが削除されて、ログアウトする際の処理
 */
@Controller
public class EditUserInfoMultiLogout{
	@Autowired
	HttpSession session; 
	/**
	 * ログインユーザーを削除しログイン画面に遷移する場合
	 * @return
	 */
	@RequestMapping(value = LOGIN_USER_DELETED,method = RequestMethod.GET)
	public String backlogin(@ModelAttribute(THERMO_FORM) ThermoForm FormValue) {
		//session一括削除
		session.invalidate();	
		
		// トップページへ移動
		return TO_TOP;
	}
}
