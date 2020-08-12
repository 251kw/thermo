package com.shantery.thermo.thermoInput;



import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.thermoInput.ThermoInputService;
import com.shantery.thermo.userInfo.UserInfoForm;
import com.shantery.thermo.userInfo.UserInfoRepository;

import antlr.collections.List;

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
	 * テスト
	 * @param mav
	 * @return
	 */
	@RequestMapping("/test")
	public ModelAndView index(ModelAndView mav) {
		Iterable<ThermoInfoEntity> list = t_repository.findAll();
		Iterable<UserInfoEntity> ulist = u_repository.findByGroupIdIs("lol");
		mav.addObject("date",list);
		return mav;
	}

	/**
	 * 検温情報入力ボタンから入力画面へ
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/from"/*FROM_SEARCH_BUTTON*/ , method = RequestMethod.POST)
	public String Input(Model model) {
		
		//TODO スマホ版に変更したときの見出しの付け方を佐藤に聞く
		
		//ログインユーザーと同じグループIDのユーザー情報受け取る
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		Iterable<UserInfoEntity> ulist = u_repository.findByGroupIdIs(loginuser.getGroup_id());
		model.addAttribute("ulist", ulist);
		model.addAttribute("thForm", new ThermoInputForm());
		return "thermoInput"/*TO_INPUT*/;
	}
	
	/**
	 * 検温情報入力から確認画面へ
	 * @param form 入力情報
	 * @param model 
	 * @return 確認画面
	 */
	@RequestMapping(value = "/from_input"/*FROM_INPUT_BUTTON*/ , method = RequestMethod.POST)
	public String InputConfirm(@ModelAttribute("thForm") ThermoInputForm form, Model model) {
		
		model.addAttribute("list", form.gettList());
		session.setAttribute("list", form.gettList());
		
		return "thermoConfirm"/*TO_INPUT_CONFIRM*/;
	}
	
	/**
	 * 検温情報確認画面から結果画面へ
	 * @return 結果画面
	 */
	@RequestMapping(value =  "/from_confirm"/*FROM_INPUT_CONFIRM_BUTTON*/ , method = RequestMethod.POST)
	public String InputResult() {
		ArrayList<ThermoInputForm.Detail> list = (ArrayList<ThermoInputForm.Detail>)session.getAttribute("list");
		
		service.registAll(list);//登録実行
		
		
		return "thermoResult"/*TO_INPUT_RESULT*/;
	}
	
	/**
	 * 登録情報入力画面へ戻る
	 * @return 入力画面
	 */
	@RequestMapping(value = "/return_input"/*FROM_RETURN_INPUT_BUTTON*/ , method = RequestMethod.GET)
	public String InputReturn() {
		
		//TODO 値の保持考える
		return "thermoInput" /*TO_INPUT*/;
	}
	
	/**
	 * 検索画面へ戻る
	 * @return 検索画面
	 */
	@RequestMapping(value = "/return_search"/*FROM_RETURN_SEARCH_BUTTON*/ , method = RequestMethod.POST)
	public String SearchReturn() {
		
		//TODO 結合で佐藤と話す
		return "search"/*TO_SEARCH*/;
	}
	
}
