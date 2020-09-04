package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.*;
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
import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author y.sato
 * 検索機能画面にあるボタンで起動するクラス
 * 
 */
@Controller
class SearchController {

	@Autowired
	private SearchService schService; //サービスクラス呼び出す
	
	@Autowired
	private SearchRepository schRepository;	//リポジトリクラス呼び出す
	
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
		List<ThermoInfoEntity> list = schRepository.searchCurDate(loginuser.getGroup_id());	//今日の日付で検索	//group_idで絞る
		boolean display = true;		//テーブルを表示させるか
		
		boolean adminbtn = schService.isAdminFlg(loginuser);	//管理者フラグがあれば検温ボタンを押せる
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute("list", list);
		
		if(list.size()==0) {	//今日の検温情報がないとき
			m.addAttribute("nolist_msg", TODAY_NOLIST_MSG);
			display = false;
			
		} else if(list.size()==MAX_SCH_LISTINT){ //検温情報が最大件数に達したとき
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		m.addAttribute("display", display);
		m.addAttribute("adminbtn", adminbtn);
		
		session.setAttribute(SCH_LIST, list);		//印刷用
		
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
		List<ThermoInfoEntity> list = null;
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
			list = schService.separate(loginuser.getGroup_id(), form);
		} else {
			list = (List<ThermoInfoEntity>) session.getAttribute(SCH_LIST);
		}
		
		if(list.size()==0) {	//検索結果がないとき
			m.addAttribute("nolist_msg", NOLIST_MSG);
			display = false;
		} else if(list.size()==MAX_SCH_LISTINT){ //検温情報が最大件数に達したとき
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		
		m.addAttribute("searchInfo", form);
		m.addAttribute("list", list);
		m.addAttribute("display", display);
		m.addAttribute("adminbtn", adminbtn);
		
		session.setAttribute(SCH_LIST, list);	//印刷用
	
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
