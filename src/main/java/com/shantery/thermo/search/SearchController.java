package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import static com.shantery.thermo.util.ThermoConstants.THERMO_FORM;
import static com.shantery.thermo.util.ThermoConstants.TO_TOP;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		List<ThermoInfoEntity> list = schRepository.searchCurDate(loginuser.getGroup_id());	//今日の日付で検索	//group_idで絞る
		boolean display = true;		//テーブルを表示させるか
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute("list", list);
		
		if(list.size()==0) {	//listがないとき
			m.addAttribute("nolist_msg", "今日の検温情報は登録されていません。");
			display = false;
			
		} else if(list.size()==50){
			m.addAttribute("overlist_msg", "50件の検索結果を表示しています。");
		}
		m.addAttribute("display", display);
		
		session.setAttribute("schlist", list);		//印刷用
		
		return "search";
	}
	
	/**
	 * 検索ボタンが押されたときに起動する
	 * メソッド
	 * @return 検索画面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/search_info" , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(/*@Validated*/ @ModelAttribute SearchInfoForm form, Model m,
			BindingResult result) {
		
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		List<ThermoInfoEntity> list = null;
		boolean display = true;		//テーブルを表示させるか
		
		//チェックボックスと他の入力欄の併用を許可しない
		if((!"".equals(form.getSch_date()) ||
				!"".equals(form.getSch_name()) ||
					!"".equals(form.getSch_grade()))&&
						(form.getSch_high()!=null)){
			
			list = (List<ThermoInfoEntity>) session.getAttribute("schlist");
			m.addAttribute("combi_msg", "チェックボックスは単体で使用してください。");
			
		} else {
		
			list = schService.separate(loginuser.getGroup_id(), form);
		}
		
		if(list.size()==0) {	//listがないとき
			if(schService.isZeroCurDate(loginuser.getGroup_id())) {	//今日の情報がない時
				m.addAttribute("nolist_msg", "今日の検温情報は登録されていません。");
			} else {
				m.addAttribute("nolist_msg", "検索結果がありませんでした。");
			}
			display = false;
		} else if(list.size()==50){
			m.addAttribute("overlist_msg", "50件の検索結果を表示しています。");
		}
		
		m.addAttribute("searchInfo", form);
		m.addAttribute("list", list);
		m.addAttribute("display", display);
		
		session.setAttribute("schlist", list);	//印刷用
	
		return "search";
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
	
	
	//TODO クリアボタンの動きおかしい
}
