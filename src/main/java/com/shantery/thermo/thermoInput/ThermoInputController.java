package com.shantery.thermo.thermoInput;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

import com.shantery.thermo.util.ThermoReplaceValue;
import static com.shantery.thermo.util.ThermoConstants.*;


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

	/**
	 * 検索のボタンから入力画面へ
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/from"/*FROM_SEARCH_BUTTON*/ , method = RequestMethod.POST)
	public String Input(Model model) {
			
		//ログインユーザーと同じグループIDのユーザー情報受け取る
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		Iterable<UserInfoEntity> ulist = u_repository.findByGroupIdIs(loginuser.getGroup_id());
		
		//ログインユーザーが今日すでに登録しているか確認
		ArrayList<ThermoInputForm.Detail> list = service.checkRegistDate(ulist);
		
		//データベースからとってきたユーザー情報を表示用に変換する 
		ArrayList<String> birth = new ArrayList<String>();
		for(UserInfoEntity ul : ulist) {
			ul.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, ul.getGender()));
			ul.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, ul.getGrade()));
			birth.add(ThermoReplaceValue.calcAge(ul.getBirthday()));	
		}
		
		model.addAttribute("list",list);
		model.addAttribute("ulist", ulist);
		model.addAttribute("birth", birth);
		model.addAttribute("thForm", new ThermoInputForm());
		session.setAttribute("ulist", ulist);
		session.setAttribute("birth", birth);
		
		return TO_THERMO_INFO_INP;
	}
	
	/**
	 * 検温情報入力から確認画面へ
	 * @param form 入力情報
	 * @param model 
	 * @return 確認画面
	 */
	@RequestMapping(value = "/from_input"/*FROM_INPUT_BUTTON*/ , method = RequestMethod.POST)
	public String InputConfirm(@ModelAttribute("thForm") ThermoInputForm form, Model model) {
		
		//入力情報を取得
		ArrayList<ThermoInputForm.Detail> list = form.gettList();
		model.addAttribute("list", list);
		session.setAttribute("list", list);
		
		//入力チェックをしてエラー文があれば入力画面へ
		ArrayList<String> message = service.checkInput(list);
		if(message.contains("※半角数字で小数第一位まで入力してください")) {
			model.addAttribute("message", message);
			model.addAttribute("ulist", (Iterable<UserInfoEntity>)session.getAttribute("ulist"));
			model.addAttribute("birth", (ArrayList<String>)session.getAttribute("birth"));
			return "thermoInput";
		}
		
		return TO_THERMO_INFO_CONF;
	}
	
	/**
	 * 検温情報確認画面から結果画面へ
	 * @return 結果画面
	 */
	@RequestMapping(value =  "/from_confirm"/*FROM_INPUT_CONFIRM_BUTTON*/ , method = RequestMethod.POST)
	public String InputResult(Model model) {
		
		//入力情報を取得
		ArrayList<ThermoInputForm.Detail> list = (ArrayList<ThermoInputForm.Detail>)session.getAttribute("list");
		
		//登録実行
		service.registAll(list);
		model.addAttribute("list", list);
		
		return TO_THERMO_INFO_RES;
	}
	
	/**
	 * 登録情報入力画面へ戻る
	 * @return 入力画面
	 */
	@RequestMapping(value = "/return_input"/*FROM_RETURN_INPUT_BUTTON*/ , method = RequestMethod.GET)
	public String InputReturn(Model model) {
		
		//初期処理をして再度入力画面へ
		model.addAttribute("list", (ArrayList<ThermoInputForm.Detail>)session.getAttribute("list"));
		model.addAttribute("ulist", (Iterable<UserInfoEntity>)session.getAttribute("ulist"));
		model.addAttribute("birth", (ArrayList<String>)session.getAttribute("birth"));
		
		return TO_THERMO_INFO_INP;
	}
	
	
}
