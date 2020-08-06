package com.shantery.thermo.search;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

@Controller
class SearchController {

	@Autowired
	private SearchService schService; //サービスクラス呼び出す
	
	@Autowired
	private SearchRepository schRepository;	//リポジトリクラス呼び出す
	@Autowired
	HttpSession session; //呼び出すクラス
	
	/**
	 *
	 * 
	 * @return
	 */

	@RequestMapping(value ="/search", method = RequestMethod.GET) //初めて検索画面に来た時
	public  String search(Model  m){
//		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute("loginuser");
//		String group_id = loginuser.getGroup_id();
		
		List<ThermoInfoEntity> schlist = schRepository.searchCurDate("1");	//今日の日付で検索	//group_idで絞る
		
		m.addAttribute("searchInfo", new SearchInfoForm());
		m.addAttribute("list", schlist);
		
		session.setAttribute("printlist", schlist);		//印刷用
		
		return "search";
	}
	
	@RequestMapping(value ="/search_info" , method = RequestMethod.POST) // 検索ボタンが押されたとき
	public String search_info(@ModelAttribute SearchInfoForm form, Model m) {
		
		String sch_date = form.getSch_date();
		String sch_name = form.getSch_name();
		String sch_grade = form.getSch_grade();
//		String sch_high = form.getSch_high();
		
		List<ThermoInfoEntity> list = schService.querySearch("1", sch_date, sch_name, sch_grade);
		m.addAttribute("list", list);
	
		return "search";
	}
	
	
}
