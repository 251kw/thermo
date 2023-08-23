package com.shantery.thermo.thermoInput;




import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.ArrayList;
import java.util.List;

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
import com.shantery.thermo.search.SearchInfoForm;
import com.shantery.thermo.search.SearchService;
import com.shantery.thermo.util.ThermoReplaceValue;


@Controller
class ThermoInputController {

	@Autowired
	HttpSession session;
	@Autowired
	ThermoInputService service;
	@Autowired
	ThermoInputRepository t_repository;
	@Autowired
	ThermoInputUserRepository u_repository;
	@Autowired
	SearchService schService; //サービスクラス呼び出す

	/**
	 * 検索のボタンから入力画面へ
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = THERMO_INFO_INP/*FROM_SEARCH_BUTTON*/ , method = RequestMethod.POST)
	public String Input(Model model) {

		//ログインユーザーと同じグループIDのユーザー情報受け取る
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		Iterable<UserInfoEntity> ulist = u_repository.findByGroupIdOrderByUpdateTime(loginuser.getGroup_id());

		//ログインユーザーが今日すでに登録しているか確認
		ArrayList<ThermoInputForm.Detail> list = service.checkRegistDate(ulist);

		//データベースからとってきたユーザー情報を表示用に変換する
		ArrayList<String> birth = new ArrayList<String>();
		for(UserInfoEntity ul : ulist) {
			ul.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, ul.getGender()));
			ul.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, ul.getGrade()));
			birth.add(ThermoReplaceValue.calcAge(ul.getBirthday())+"歳");
		}

		model.addAttribute(THERMO_LIST ,list);
		model.addAttribute(THERMO_USER_LIST, ulist);
		model.addAttribute(THERMO_BIRTH, birth);
		model.addAttribute(THERMO_INP_FORM, new ThermoInputForm());
		model.addAttribute("searchInfo", new SearchInfoForm());
		session.setAttribute(THERMO_USER_LIST, ulist);
		session.setAttribute(THERMO_BIRTH, birth);

		return TO_THERMO_INFO_INP;
	}

	/**
	 * 検温情報入力から確認画面へ
	 * @param form 入力情報
	 * @param model
	 * @return 確認画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = THERMO_INFO_INP_SUC, method = RequestMethod.POST)
	public String InputConfirm(@Validated @ModelAttribute(THERMO_INP_FORM) ThermoInputForm form, BindingResult result, Model model) {

		//入力情報を取得
		ArrayList<ThermoInputForm.Detail> list = form.gettList();
		//体温が3桁の時、小数点を入れる処理
		list = service.adjustThermo(list);

		model.addAttribute(THERMO_LIST , list);
		session.setAttribute(THERMO_LIST , list);

		//入力チェックをしてエラー文があれば入力画面へ
		ArrayList<String> message = service.checkInput(list);
		if(message.contains(THERMO_INP_ER) || message.contains(THERMO_INP_TEMP_ER) || result.hasErrors()) {
			model.addAttribute("message", message);
			model.addAttribute(THERMO_USER_LIST, (Iterable<UserInfoEntity>)session.getAttribute(THERMO_USER_LIST));
			model.addAttribute(THERMO_BIRTH, (ArrayList<String>)session.getAttribute(THERMO_BIRTH));
			model.addAttribute(THERMO_INP_FORM, new ThermoInputForm());
			model.addAttribute("searchInfo", session.getAttribute("shForm"));
			if(session.getAttribute("shForm")==null) {
				model.addAttribute("searchInfo", new SearchInfoForm());
			}
			return TO_THERMO_INFO_INP;
		}

		return TO_THERMO_INFO_CONF;
	}

	/**
	 * 検温情報確認画面から結果画面へ
	 * @return 結果画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = THERMO_INFO_CONF_SUC, method = RequestMethod.POST)
	public String InputResult(Model model) {

		//入力情報を取得
		ArrayList<ThermoInputForm.Detail> list = (ArrayList<ThermoInputForm.Detail>)session.getAttribute(THERMO_LIST);

		//登録実行
		service.registAll(list);
		model.addAttribute(THERMO_LIST , list);

		return TO_THERMO_INFO_RES;
	}

	/**
	 * 検索ボタンが押された時に起動する
	 * メソッド
	 * @return 検索画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/search" , method = RequestMethod.POST)
	public String search_info(@ModelAttribute("searchInfo") @Validated SearchInfoForm form, BindingResult result, Model model) {

		Iterable<UserInfoEntity> ulist = (Iterable<UserInfoEntity>)session.getAttribute(THERMO_USER_LIST);
		//errorがなければ検索
		if(!result.hasErrors()) {
			ulist = service.searchUserList(ulist, form);
		}
		if(((List<UserInfoEntity>)ulist).size()==0) {	//検索結果がないとき
			model.addAttribute("nolist_msg", NOLIST_MSG);
		}


		//ログインユーザーが今日すでに登録しているか確認
		ArrayList<ThermoInputForm.Detail> list = service.checkRegistDate(ulist);
		//データベースからとってきたユーザー情報を表示用に変換する
		ArrayList<String> birth = new ArrayList<String>();
		if(!result.hasErrors()) {
			for(UserInfoEntity ul : ulist) {
				ul.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, ul.getGender()));
				ul.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, ul.getGrade()));
				birth.add(ThermoReplaceValue.calcAge(ul.getBirthday())+"歳");
			}
		}else {
			birth = (ArrayList<String>)session.getAttribute(THERMO_BIRTH);
		}
		model.addAttribute(THERMO_LIST , list);
		model.addAttribute(THERMO_USER_LIST, ulist);
		model.addAttribute(THERMO_BIRTH, birth);
		model.addAttribute(THERMO_INP_FORM, new ThermoInputForm());
		model.addAttribute("searchInfo", form);
		session.setAttribute("shForm", form);
		session.setAttribute(THERMO_USER_LIST, ulist);
		session.setAttribute(THERMO_BIRTH, birth);

		return TO_THERMO_INFO_INP;
	}


	/**
	 * 登録情報入力画面へ戻る
	 * @return 入力画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = THERMO_INFO_CONF_BACK, method = RequestMethod.GET)
	public String InputReturn(Model model) {

		//初期処理をして再度入力画面へ
		model.addAttribute(THERMO_LIST , (ArrayList<ThermoInputForm.Detail>)session.getAttribute(THERMO_LIST));
		model.addAttribute(THERMO_USER_LIST, (Iterable<UserInfoEntity>)session.getAttribute(THERMO_USER_LIST));
		model.addAttribute(THERMO_BIRTH, (ArrayList<String>)session.getAttribute(THERMO_BIRTH));
		model.addAttribute(THERMO_INP_FORM, new ThermoInputForm());
		model.addAttribute("searchInfo", session.getAttribute("shForm"));
		if(session.getAttribute("shForm")==null) {
			model.addAttribute("searchInfo", new SearchInfoForm());
		}

		return TO_THERMO_INFO_INP;
	}


}
