package com.shantery.thermo.editUserInfoMulti;


import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import static com.shantery.thermo.util.ThermoConstants.THERMO_FORM;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.entity.UserInfoEntity;


@Controller
class EditUserInfoMultiController {

	@Autowired
	private EditUserInfoMultiService service;
	
	@Autowired
	EditUserInfoMultiRepository repository;
	
	@Autowired
	HttpSession session; 
	
	@RequestMapping(value = "/editusersmultiset", method = RequestMethod.GET)
	public String storeInfo(
			UserInfoEntity userinfo,
			Model model){

		EditUserInfoMultiForm userlist = new EditUserInfoMultiForm();
		ArrayList<UserInfoEntity> original = new ArrayList<UserInfoEntity>();
		//Optional<UserInfoEntity> data = null;
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		original = service.storeOriginalInfo(loginuser.getGroup_id(),userinfo,original);
		
		userlist.settList(original);
		
		model.addAttribute("userlist", userlist.gettList());

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiInput";
	}	
		
		
	@RequestMapping(value = "/editusersmulticonfirm", method = RequestMethod.POST)
	public String receiveInfo(
			@ModelAttribute("userlist") EditUserInfoMultiForm userlist,
			BindingResult result,
			Model model){
		
		model.addAttribute("userlist", userlist.gettList());

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiInput";
	}
}
