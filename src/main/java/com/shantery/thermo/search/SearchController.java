package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import static com.shantery.thermo.util.ThermoConstants.THERMO_FORM;
import static com.shantery.thermo.util.ThermoConstants.TO_TOP;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		List<ThermoInfoEntity> list = schRepository.searchCurDate(loginuser.getGroup_id());	//今日の日付で検索	//group_idで絞る
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute("list", list);
		
		session.setAttribute("schlist", list);		//印刷用
		
		return "search";
	}
	
	/**
	 * 検索ボタンが押されたときに起動する
	 * メソッド
	 * @return 検索画面
	 */
	@RequestMapping(value ="/search_info" , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(@ModelAttribute SearchInfoForm form, Model m) {
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		List<ThermoInfoEntity> list = schService.separate(loginuser.getGroup_id(), form);
		boolean display = true;
		
		
		if(list.size()==0) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String curDate = sdf.format(cal.getTime());		//今日の日付
			if((form.getSch_date()).equals(curDate)) {
				m.addAttribute("nolist_msg", "今日の検温情報が登録されていません。");
			} else {
				m.addAttribute("nolist_msg", "検索結果がありませんでした。");
			}
			display = false;
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
