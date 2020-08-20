package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.*;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequestMapping(value ="/return_search", method = RequestMethod.GET) //検索画面に来た時(戻る)
	public  String search(Model  m){
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		List<ThermoInfoEntity> list = schRepository.searchCurDate(loginuser.getGroup_id());	//今日の日付で検索	//group_idで絞る
		boolean display = true;		//テーブルを表示させるか
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute("list", list);
		
		if(list.size()==0) {	//listがないとき
			m.addAttribute("nolist_msg", TODAY_NOLIST_MSG);
			display = false;
			
		} else if(list.size()==50){
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		m.addAttribute("display", display);
		
		session.setAttribute(SCH_LIST, list);		//印刷用
		
		return TO_SEARCH;
	}
	
	/**
	 * 検索ボタンが押されたときに起動する
	 * メソッド
	 * @return 検索画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/search_info" , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(@ModelAttribute("searchInfo") SearchInfoForm form, Model m) {
		
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		List<ThermoInfoEntity> list = null;
		boolean display = true;		//テーブルを表示させるか
		String error_msg = null;
		
		boolean adminbtn = schService.isAdminFlg(loginuser);	//管理者フラグがあれば検温ボタンを押せる
		
		//入力チェックでエラーがあったら(未記入はスルーする）
		if(!schService.strCheck(form.getSch_name())&&(!form.getSch_name().equals(EMPTY))) {
			list = (List<ThermoInfoEntity>)session.getAttribute(SCH_LIST);
			error_msg = INFO_ERROR;
		}
		//チェックボックスと他の入力欄の併用を許可しない
		else if((!EMPTY.equals(form.getSch_date()) ||
				!EMPTY.equals(form.getSch_name()) ||
					!EMPTY.equals(form.getSch_grade()))&&
						(form.getSch_high()!=null)){
			
			list = (List<ThermoInfoEntity>) session.getAttribute(SCH_LIST);
			m.addAttribute("combi_msg", COMBI_MSG);
			
		} else {
		
			list = schService.separate(loginuser.getGroup_id(), form);
		}
		
		if(list.size()==0) {	//listがないとき
			if(schService.isZeroCurDate(loginuser.getGroup_id())) {	//今日の情報がない時
				m.addAttribute("nolist_msg", TODAY_NOLIST_MSG);
			} else {
				m.addAttribute("nolist_msg", NOLIST_MSG);
			}
			display = false;
		} else if(list.size()==50){
			m.addAttribute("overlist_msg", OVER_LIST_MSG);
		}
		
		m.addAttribute("searchInfo", form);
		m.addAttribute("list", list);
		m.addAttribute("display", display);
		m.addAttribute("error_msg", error_msg);
		m.addAttribute("adminbtn", adminbtn);
		
		session.setAttribute(SCH_LIST, list);	//印刷用
	
		return TO_SEARCH;
	}
	
	/**
	 * ログアウトボタンが押されたときに起動する
	 * メソッド
	 * @return ログイン画面
	 */
	@RequestMapping(value ="/logout", method = RequestMethod.GET) //ログアウト
	public String logout(	//TODO 仮ログアウト
			@ModelAttribute(THERMO_FORM) 
			ThermoForm FormValue){	// この時点では空
		
		// もしsessionスコープ内にデータがあるなら削除する
		if (session.getAttribute(LOGIN_USER) != null) {
			session.removeAttribute(LOGIN_USER);
		}
			
		// トップページへ移動
		return TO_TOP;
	}
	
	
}
