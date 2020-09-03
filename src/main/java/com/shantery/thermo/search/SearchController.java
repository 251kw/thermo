package com.shantery.thermo.search;

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

import com.shantery.thermo.ThermoForm;
//import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.thermoInput.ThermoInputForm;
import com.shantery.thermo.thermoInput.ThermoInputService;
import com.shantery.thermo.thermoInput.ThermoInputUserRepository;
import com.shantery.thermo.util.ThermoReplaceValue;

/**
 * @author y.sato
 * 検索機能画面にあるボタンで起動するクラス
 * 
 */
@Controller
class SearchController {

	@Autowired
	private SearchService schService; //サービスクラス呼び出す
	
//	@Autowired
//	private SearchRepository schRepository;	//リポジトリクラス呼び出す
	
	@Autowired
	ThermoInputUserRepository thmInUserRepository; //
	
	@Autowired
	ThermoInputService thmInService; //
	
	@Autowired
	HttpSession session; 
	
	/**
	 * 検索画面に戻ってきたときに起動する
	 * メソッド
	 * @return 検索画面
	 */
	@RequestMapping(value =SEARCH_RETURN, method = RequestMethod.GET) //検索画面に来た時(戻る)
	public  String search(Model  m){
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		//グループIDからとってくるに変更
		Iterable<UserInfoEntity> ulist = thmInUserRepository.findByGroupIdOrderByUpdateTime(loginuser.getGroup_id());
		
		
		//ログインユーザーたちが今日すでに登録しているか確認
		ArrayList<ThermoInputForm.Detail> list = thmInService.checkRegistDate(ulist);
		
		
		//データベースからとってきたユーザー情報を表示用に変換する 
		ArrayList<String> birth = new ArrayList<String>();
		for(UserInfoEntity ul : ulist) {
			ul.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, ul.getGender()));
			ul.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, ul.getGrade()));
			birth.add(ThermoReplaceValue.calcAge(ul.getBirthday())+"歳");	
		}
				
		boolean display = true;
		
		boolean adminbtn = schService.isAdminFlg(loginuser);	//管理者フラグがあれば検温ボタンを押せる
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute(THERMO_LIST ,list);
		m.addAttribute(THERMO_USER_LIST, ulist);
		m.addAttribute(THERMO_BIRTH, birth);
		m.addAttribute(THERMO_INP_FORM, new ThermoInputForm());
		session.setAttribute(THERMO_USER_LIST, ulist);
		session.setAttribute(THERMO_BIRTH, birth);
		
		/*if(list.size()==0) {	//今日の検温情報がないとき
			m.addAttribute("nolist_msg", TODAY_NOLIST_MSG);
			display = false;
			
		}*/
		
		if(list.size()==MAX_SCH_LISTINT){ //検温情報が最大件数に達したとき
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		m.addAttribute("display", display);
		m.addAttribute("adminbtn", adminbtn);
		
		session.setAttribute(SCH_LIST, list);		//印刷用
		
		session.removeAttribute("uslist");
		session.removeAttribute("uForm");

		
		return TO_SEARCH;
	}
	
	/**
	 * 検索ボタンが押されたときに起動する
	 * メソッド
	 * @return 検索画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value =SEARCH_INFO , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(@ModelAttribute("searchInfo") @Validated SearchInfoForm form, BindingResult result, Model m) {
		
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		List<UserInfoEntity> ulist = null;
		boolean display = true;		//テーブルを表示させるか
		boolean errorFlg = false;
		
		boolean adminbtn = schService.isAdminFlg(loginuser);	//管理者フラグがあれば検温ボタンを押せる
		
		//名前入力チェックでエラーがあったら(未記入はスルーする）
		if(result.hasErrors()) {
			errorFlg = true;
		}
		
		//チェックボックスと他の入力欄の併用を許可しない
		if((!EMPTY.equals(form.getSch_date()) ||
				!EMPTY.equals(form.getSch_name()) ||
					!EMPTY.equals(form.getSch_grade()))&&
						(form.getSch_high()!=null)){
			
			m.addAttribute("combi_msg", COMBI_MSG);
			errorFlg = true;
		}
		
		//errorがなければ検索
		if(!errorFlg) {
			ulist = schService.userSearch(loginuser.getGroup_id(), form);
		} else {
//			list = (List<ThermoInfoEntity>) session.getAttribute(SCH_LIST);
		}
		
		if(ulist.size()==0) {	//検索結果がないとき
			m.addAttribute("nolist_msg", NOLIST_MSG);
			display = false;
		} else if(ulist.size()==MAX_SCH_LISTINT){ //検温情報が最大件数に達したとき
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		
		//ulistを元に検温情報があるかチェック
		ArrayList<ThermoInputForm.Detail> list = thmInService.checkRegistDate(ulist);
		
		m.addAttribute("searchInfo", form);
		m.addAttribute("display", display);
		m.addAttribute("adminbtn", adminbtn);
		
		m.addAttribute(THERMO_LIST ,list);
		m.addAttribute(THERMO_USER_LIST, ulist);
		
		session.setAttribute(SCH_LIST, ulist);	//印刷用
	
		return TO_SEARCH;
	}
	
	
	/**
	 * 更新ボタンが押された時に
	 * 起動するメソッド
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = THERMO_INFO_INP_SUC, method = RequestMethod.POST)
	public String InputConfirm(@Validated @ModelAttribute(THERMO_INP_FORM) ThermoInputForm form, BindingResult result, Model model) {
		
		//入力情報を取得
		ArrayList<ThermoInputForm.Detail> list = form.gettList();
		model.addAttribute(THERMO_LIST , list);
		session.setAttribute(THERMO_LIST , list);
		model.addAttribute(THERMO_USER_LIST, (Iterable<UserInfoEntity>)session.getAttribute(THERMO_USER_LIST));
		model.addAttribute(THERMO_BIRTH, (ArrayList<String>)session.getAttribute(THERMO_BIRTH));
		boolean display = true;
		model.addAttribute("display", display);
		model.addAttribute("searchInfo", new SearchInfoForm());
		
		
		//入力チェックをしてエラー文があれば入力画面へ
		ArrayList<String> message = thmInService.checkInput(list);
		if(message.contains(THERMO_INP_ER) || message.contains(THERMO_INP_TEMP_ER) || result.hasErrors()) {
			model.addAttribute("message", message);
			return TO_THERMO_INFO_INP;
		}
		
		//登録実行
		thmInService.registAll(list);
		
		return TO_SEARCH;
	}
	
	
	/**
	 * ログアウトボタンが押されたときに起動する
	 * メソッド
	 * @return ログイン画面
	 */
	@RequestMapping(value =LOGOUT, method = RequestMethod.GET)
	public String logout(@ModelAttribute(THERMO_FORM) ThermoForm FormValue){	
		
		session.removeAttribute(LOGIN_USER);
		session.removeAttribute(SCH_LIST);
			
		// トップページへ移動
		return TO_TOP;
	}
	
}
