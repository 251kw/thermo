package com.shantery.thermo.search;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	 * @return
	 */
	@RequestMapping(value ="/search_info" , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(@ModelAttribute SearchInfoForm form, Model m) {
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
		List<ThermoInfoEntity> list = schService.separate(loginuser.getGroup_id(), form);
		
		m.addAttribute("searchInfo", form);
		m.addAttribute("list", list);
		
		session.setAttribute("schlist", list);	//印刷用
	
		return "search";
	}
	
//	@RequestMapping(value ="/logout", method = RequestMethod.GET) //ログアウト
//	public  String logout(Model  m){
//		
//		return "index";
//	}
	
}
