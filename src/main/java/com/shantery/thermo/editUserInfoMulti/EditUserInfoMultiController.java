package com.shantery.thermo.editUserInfoMulti;


import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.editUserInfoMulti.EditUserInfoMultiForm.contents;
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
	public String storeInfo(
			 @ModelAttribute("userlist") EditUserInfoMultiForm form,
			Model model){

		// sessionを空にする
		if (session.getAttribute("inputlist") != null) {
			session.removeAttribute("inputlist");
		}
		if (session.getAttribute("originallist") != null) {
			session.removeAttribute("originallist");
		}
		
		ArrayList<UserInfoEntity> original = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> formlist = new ArrayList<>();
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		original = service.storeOriginalInfo(loginuser.getGroup_id(),original);
		
		formlist = service.makeFormList(formlist, original);
		
		form.setUserList(formlist);
		
		model.addAttribute("userlist", form.getUserList());
		session.setAttribute("originallist", formlist);

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiInput";
	}	
		
	// 入力から確認ボタン
	@RequestMapping(value = "/editusersmulticonfirm", method = RequestMethod.POST)
	public String receiveInfo(
			@Validated @ModelAttribute("userlist") EditUserInfoMultiForm form,
			BindingResult result,
			Model model){

		String errormsg = null;
		ArrayList<EditUserInfoMultiForm.contents> originallist;
		originallist = (ArrayList<EditUserInfoMultiForm.contents>)session.getAttribute("originallist");
		ArrayList<EditUserInfoMultiForm.contents> updatelist = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> inputlist = new ArrayList<>();
		inputlist = form.getUserList();
		updatelist = service.makeUpdateList(updatelist, inputlist, originallist);
		
		String transition = null;
		transition = service.setTransition(updatelist, transition);
		
		if(transition.equals("editUserInfoMultiInput")) {
			
			errormsg = service.setErrorMsg(errormsg);
			model.addAttribute("errormsg", errormsg);
		}
		
		model.addAttribute("userlist", form.getUserList());
		session.setAttribute("inputlist", inputlist);
		session.setAttribute("updatelist", updatelist);
		
		// ユーザー情報一括変更確認画面へ移動
		return transition;
	}
	
	// 確認画面から戻る
	@RequestMapping(value = "/editusersmultireturn", method = RequestMethod.GET)
	public String returnInfo(
			Model model){
		
		// ユーザー情報一括入力画面へ移動
		model.addAttribute("userlist", session.getAttribute("inputlist"));
		session.removeAttribute("updatelist");
		session.removeAttribute("inputlist");
		return "editUserInfoMultiInput";
	}
	
	// 確認画面から結果画面
	@RequestMapping(value = "/editusersmultiresult", method = RequestMethod.POST)
	public String updateInfo(@ModelAttribute("userlist") EditUserInfoMultiForm form){
		
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		ArrayList<UserInfoEntity> registlist = new ArrayList<>();
		
		registlist = service.makeRegistList(form.getUserList(), loginuser.getGroup_id());
		
		service.registInfo(registlist);
		
		Optional<UserInfoEntity> userinfo = repository.findById(loginuser.getUser_id());
		session.setAttribute(LOGIN_USER, userinfo);
		
		// ユーザー情報一括変更結果画面へ移動
		return "editUserInfoMultiResult";
	}
	
	@RequestMapping(value = "/get_checkbox", method = RequestMethod.GET)
	public String getCheckValue(
			@ModelAttribute("userlist") EditUserInfoMultiForm form,
			Model model){
		
		return "index";
		
	}
}
