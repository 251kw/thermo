package com.shantery.thermo.editUserInfoMulti;


import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	// 初期処理
	@RequestMapping(value = "/editusersmultiset", method = RequestMethod.GET)
	public String storeInfo(){

		// sessionを空にする
		if (session.getAttribute("userlist") != null) {
			session.removeAttribute("userlist");
		}
		if (session.getAttribute("originallist") != null) {
			session.removeAttribute("originallist");
		}
		
		//EditUserInfoMultiForm form = new EditUserInfoMultiForm();
		ArrayList<UserInfoEntity> original = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> formlist = new ArrayList<>();
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		original = service.storeOriginalInfo(loginuser.getGroup_id(),original);
		
		formlist = service.makeFormList(formlist, original);
		
		
		session.setAttribute("userlist", formlist);
		session.setAttribute("originallist", formlist);

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiInput";
	}	
		
	// 入力から確認ボタン
	@RequestMapping(value = "/editusersmulticonfirm", method = RequestMethod.POST)
	public String receiveInfo(
			@ModelAttribute("userlist") EditUserInfoMultiForm form,
			Model model){

		ArrayList<EditUserInfoMultiForm.contents> originallist;
		originallist = (ArrayList<EditUserInfoMultiForm.contents>)session.getAttribute("originallist");
		ArrayList<EditUserInfoMultiForm.contents> updatelist = new ArrayList<>();
		updatelist = service.makeUpdateList(updatelist, form.getUserList(), originallist);
		
		session.setAttribute("userlist", form.getUserList());
		session.setAttribute("updatelist", updatelist);

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiConfirm";
	}
	
	// 確認画面から戻る
	@RequestMapping(value = "/editusersmultireturn", method = RequestMethod.GET)
	public String returnInfo(){
		
		// ユーザー情報一括変更画面へ移動
		session.removeAttribute("updatelist");
		return "editUserInfoMultiInput";
	}
	
	// 確認画面から結果画面
	@RequestMapping(value = "/editusersmultiresult", method = RequestMethod.POST)
	public String updateInfo(@ModelAttribute("userlist") EditUserInfoMultiForm form){
		
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		ArrayList<UserInfoEntity> registlist = new ArrayList<>();
		
		registlist = service.makeRegistList(form.getUserList(), loginuser.getGroup_id());
		
		service.registInfo(registlist);
		
		// ユーザー情報一括変更結果画面へ移動
		return "editUserInfoMultiResult";
	}
}
