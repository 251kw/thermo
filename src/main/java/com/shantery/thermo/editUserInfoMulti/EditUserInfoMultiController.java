package com.shantery.thermo.editUserInfoMulti;


import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.ArrayList;
import java.util.Collections;
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

import com.shantery.thermo.ThermoForm;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.util.ThermoReplaceValue;


/**
 * @author k.takahashi
 * ユーザー情報一括変更画面での処理
 */
@Controller
@RequestMapping(EDIT_USER_INFO_MULTI_INP)
class EditUserInfoMultiController {

	@Autowired
	private EditUserInfoMultiService service;

	@Autowired
	EditUserInfoMultiRepository repository;

	@Autowired
	EditUserInfoMultiRepositoryThermo t_repository;

	@Autowired
	HttpSession session;

	/**
	 * 検索画面から遷移してきた時の処理
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = EDIT_USER_INFO_MULTI_SET, method = RequestMethod.GET)
	public String storeInfo(
			Model model,EditUserInfoMultiForm form){

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
		if (session.getAttribute("delusers") != null) {
			session.removeAttribute("delusers");
		}
		if (session.getAttribute("deluserList") != null) {
			session.removeAttribute("deluserList");
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
		return TO_EDIT_USER_INFO_MULTI_INP;
	}

	/**
	 * 削除確認画面へ遷移
	 * チェックされたユーザーの情報を渡す
	 * @param form
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = EDIT_USER_INFO_MULTI_CONF, params = EDIT_USER_INFO_MULTI_DELETED, method = RequestMethod.POST)
	public String deleteconf(EditUserInfoMultiForm form,
			Model model) {
		ArrayList<EditUserInfoMultiForm.contents> originallist;
		ArrayList<EditUserInfoMultiForm.contents> updatelist = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> inputlist = new ArrayList<>();
		// セッションから比較用リストを取得
		originallist = (ArrayList<EditUserInfoMultiForm.contents>)session.getAttribute("originallist");

		// 入力画面の情報を全て取得
		inputlist = form.getUserList();

		// ２つのリストを比較して変更されたユーザーのみを取得
		updatelist = service.makeUpdateList(updatelist, inputlist, originallist);

		//チェックされたユーザーの情報保持
		String[] deluser = form.getInputMultiCheck();
		String errormsg = null;
		ArrayList<UserInfoEntity> delUserList = new ArrayList<>();
		//削除されるユーザーの有無を確認
		if(deluser == null || deluser.length == 0) {
			errormsg = "チェックボックスを入力してください";
			// データを格納
			model.addAttribute("userlist", form.getUserList());
			model.addAttribute("errormsg",errormsg);
			//削除されるユーザーを選択していない場合Input画面へ遷移
			return TO_EDIT_USER_INFO_MULTI_INP;
		}else{
			//削除されるユーザーの情報のリストを作成
			for (int i = 0; i <= deluser.length - 1; i++) {
				delUserList.addAll(0, repository.UserDelList(form.getInputMultiCheck()[i]));
			}
			Collections.reverse(delUserList); // lst自体を変更
			session.setAttribute("delusers", deluser); //削除用
			session.setAttribute("delUserList",delUserList); //確認・完了画面表示用
			model.addAttribute("form",form);
			session.setAttribute("inputlist", inputlist);
			session.setAttribute("updatelist", updatelist);
			return TO_EDIT_USER_INFO_MULTI_DEL_COMF;
		}
	}

	/**
	 * 削除完了画面に遷移
	 * ユーザーとその検温情報を削除する
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = EDIT_USER_INFO_MULTI_DEL_REZ,method = RequestMethod.POST)
	public String deleteresult(@ModelAttribute("userlist") EditUserInfoMultiForm form,
			Model model) {
		String[] delusers = (String[]) session.getAttribute("delusers"); //削除されるユーザーのログインID
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		int flag = 0;
		//削除するユーザーにログインユーザーがいないか調べる
		for (int i = 0; i <= delusers.length - 1; i++) {
			if(delusers[i].equals(loginuser.getUser_id())) {
				flag = 1;
			}
		}
		//検温情報を削除
		for (int i = 0; i <= delusers.length - 1; i++) {
			t_repository.thermoDel(delusers[i]);
		}
		//ユーザーを削除
		for (int i = 0; i <= delusers.length - 1; i++) {
			repository.deleteById(delusers[i]);
		}
		model.addAttribute("flag", flag);
		return TO_EDIT_USER_INFO_MULTI_DEL_RES;
	}

	/**
	 * 削除確認画面から戻るが押された場合
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping(value = EDIT_USER_INFO_MULTI_DEL_REZ, params= EDIT_USER_INFO_MULTI_DEL_BACK, method = RequestMethod.POST)
	public String returnuerdel(
			Model model,EditUserInfoMultiForm form){

		// 値保持のために入力情報が全て格納されているリストをバインド
		model.addAttribute("userlist", form.getUserList());

		// 役割のなくなったリストをセッションから削除
		session.removeAttribute("updatelist");
		session.removeAttribute("inputlist");

		// ユーザー情報一括変更画面へ移動
		return TO_EDIT_USER_INFO_MULTI_INP;
	}

	/**
	 * ログインユーザーを削除しログイン画面に遷移する場合
	 * @return
	 */
	@RequestMapping(value = EDIT_USER_INFO_MULTI_SET,params = EDIT_USER_INFO_MULTI_DEL_LOGOUT, method = RequestMethod.GET)
	public String backlogin(@ModelAttribute(THERMO_FORM) ThermoForm FormValue) {
		//session一括削除
		session.invalidate();

		// トップページへ移動
		return TO_TOP;
	}

	/**
	 * ユーザー更新確認画面へ遷移する
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = EDIT_USER_INFO_MULTI_CONF, params = EDIT_USER_INFO_MULTI_CONF_UPDATE, method = RequestMethod.POST)
	public String receiveInfo(
			@Validated  EditUserInfoMultiForm form,
			BindingResult result,
			Model model){
		ArrayList<String[]> errlist = new ArrayList<>();
		Boolean nullcheck = false;
		String transition = null;
		ArrayList<EditUserInfoMultiForm.contents> originallist;
		ArrayList<EditUserInfoMultiForm.contents> updatelist = new ArrayList<>();
		ArrayList<EditUserInfoMultiForm.contents> inputlist = new ArrayList<>();

		// セッションから比較用リストを取得
		originallist = (ArrayList<EditUserInfoMultiForm.contents>)session.getAttribute("originallist");
		for (int i = 0; i <= form.getUserList().size() - 1; i++) {
			form.getUserList().get(i).setUser_name(ThermoReplaceValue.trimBlank(form.getUserList().get(i).getUser_name()));;
		}
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
				transition = TO_EDIT_USER_INFO_MULTI_INP;
				model.addAttribute("errlist", errlist);

			// errlistが空なら確認画面へ移動
			}else {
				transition = TO_EDIT_USER_INFO_MULTI_COMF;
			}

		// updatelistの中身がない場合
		}else {
			// そのまま確認画面へ
			transition = TO_EDIT_USER_INFO_MULTI_COMF;
		}
		model.addAttribute("form",form);
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
	@RequestMapping(value = EDIT_USER_INFO_MULTI_REZ,params=EDIT_USER_INFO_MULTI_CONF_RETURN, method = RequestMethod.POST)
	public String returnInfo(
			Model model,EditUserInfoMultiForm form){

		// 値保持のために入力情報が全て格納されているリストをバインド
		model.addAttribute("userlist", session.getAttribute("inputlist"));

		// 役割のなくなったリストをセッションから削除
		session.removeAttribute("updatelist");
		session.removeAttribute("inputlist");

		// ユーザー情報一括入力画面へ移動
		return TO_EDIT_USER_INFO_MULTI_INP;
	}

	/**
	 * 確認画面の変更ボタンを押された時の処理
	 * @param form
	 * @return 結果画面
	 */
	@RequestMapping(value = EDIT_USER_INFO_MULTI_REZ, method = RequestMethod.POST)
	public String updateInfo(@ModelAttribute("userlist") EditUserInfoMultiForm form, Model model){

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
		return TO_EDIT_USER_INFO_MULTI_RES;
	}
}
