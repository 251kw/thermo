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
import com.shantery.thermo.entity.UserInfoEntity;


/**
 * @author k.takahashi
 * ユーザー情報一括変更画面での処理
 */
@Controller
class EditUserInfoMultiController {

	@Autowired
	private EditUserInfoMultiService service;
	
	@Autowired
	EditUserInfoMultiRepository repository;
	
	@Autowired
	HttpSession session; 
	
	/**
	 * 検索画面から遷移してきた時の処理
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/editusersmultiset", method = RequestMethod.GET)
	public String storeInfo(
			Model model){

		// sessionを空にする
		if (session.getAttribute("inputlist") != null) {
			session.removeAttribute("inputlist");
		}
		if (session.getAttribute("originallist") != null) {
			session.removeAttribute("originallist");
		}
		if (session.getAttribute("updatelist") != null) {
			session.removeAttribute("updatelist");
		}
		
		ArrayList<UserInfoEntity> original = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> formlist = new ArrayList<>();
		
		// ログイン中のユーザー情報を取得
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		// 初期表示用のデータを取得
		original = service.storeOriginalInfo(loginuser.getGroup_id(),original);
		
		// 取得したデータをformに詰め替える
		formlist = service.makeFormList(formlist, original);
		
		// データを格納
		model.addAttribute("userlist", formlist);		// 表示用
		session.setAttribute("originallist", formlist);	// 比較用

		// ユーザー情報一括変更画面へ移動
		return "editUserInfoMultiInput";
	}	
		
	/**
	 * 入力画面の確認ボタンを押された時の処理
	 * @param form
	 * @param result
	 * @param model
	 * @return 遷移先
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/editusersmulticonfirm", method = RequestMethod.POST)
	public String receiveInfo(
			@Validated @ModelAttribute("userlist") EditUserInfoMultiForm form,
			BindingResult result,
			Model model){

		ArrayList<String[]> errlist = new ArrayList<>();
		String errormsg = null;
		Boolean nullcheck = false;
		String transition = null;
		ArrayList<EditUserInfoMultiForm.contents> originallist;
		ArrayList<EditUserInfoMultiForm.contents> updatelist = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> inputlist = new ArrayList<>();
		
		// セッションから比較用リストを取得
		originallist = (ArrayList<EditUserInfoMultiForm.contents>)session.getAttribute("originallist");

		// 入力画面の情報を全て取得
		inputlist = form.getUserList();
		
		// ２つのリストを比較して変更されたユーザーのみを取得
		updatelist = service.makeUpdateList(updatelist, inputlist, originallist);
			
		// updatelistの中身がある場合
		if(!(updatelist.isEmpty())) {
			
			// 変更箇所を入力チェックして、エラーメッセージリストを作成
			errlist = service.checkInputList(inputlist, errlist);
			
			// errlistのnull部分を排除した結果、空のリストができたなら値は正常
			nullcheck = service.checkNull(errlist, nullcheck);
			
			// errlistが空でなかったのなら入力画面に戻す
			if(nullcheck==false) {
				transition = "editUserInfoMultiInput";
				model.addAttribute("errlist", errlist);
				
			// errlistが空なら確認画面へ移動
			}else {
				transition = "editUserInfoMultiConfirm";
			}
			
		// updatelistの中身がない場合
		}else {
			// そのまま確認画面へ
			transition = "editUserInfoMultiConfirm";
		}
		
		model.addAttribute("userlist", form.getUserList());
		session.setAttribute("inputlist", inputlist);
		session.setAttribute("updatelist", updatelist);
		
		// 画面移動
		return transition;
	}
	
	/**
	 * 確認画面の戻るボタンを押された時の処理
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/editusersmultireturn", method = RequestMethod.GET)
	public String returnInfo(
			Model model){
		
		// 値保持のために入力情報が全て格納されているリストをバインド
		model.addAttribute("userlist", session.getAttribute("inputlist"));
		
		// 役割のなくなったリストをセッションから削除
		session.removeAttribute("updatelist");
		session.removeAttribute("inputlist");
		
		// ユーザー情報一括入力画面へ移動
		return "editUserInfoMultiInput";
	}
	
	/**
	 * 確認画面の変更ボタンを押された時の処理
	 * @param form
	 * @return 結果画面
	 */
	@RequestMapping(value = "/editusersmultiresult", method = RequestMethod.POST)
	public String updateInfo(@ModelAttribute("userlist") EditUserInfoMultiForm form){
		
		ArrayList<UserInfoEntity> registlist = new ArrayList<>();
		
		// ログイン中のユーザー情報を取得
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		// データベースの形式にリストを整形
		registlist = service.makeRegistList(form.getUserList(), loginuser.getGroup_id());
		
		// データベースを更新
		service.registInfo(registlist);
		
		// ログイン中のユーザー情報が変更されている可能性があるため、再取得
		Optional<UserInfoEntity> data = repository.findById(loginuser.getUser_id());
		UserInfoEntity userinfo = data.get();
		session.setAttribute(LOGIN_USER, userinfo);
		
		// ユーザー情報一括変更結果画面へ移動
		return "editUserInfoMultiResult";
	}
	
	
	
	
	// TODO 削除機能を作ろうとした実験の名残
	@RequestMapping(value = "/get_checkbox", method = RequestMethod.GET)
	public String getCheckValue(
			@ModelAttribute("userlist") EditUserInfoMultiForm form,
			Model model){
		
		return "index";
		
	}
}
