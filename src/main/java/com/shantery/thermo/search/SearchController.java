package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.List;

//import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.bind.annotation.RequestParam;

//import com.shantery.thermo.util.ThermoUtil;

@Controller
class SearchController {

//	@Autowired
//	private SearchService schService; //サービスクラス呼び出す
	
	@Autowired
	private SearchRepository schRepository;	//リポジトリクラス呼び出す
//	@Autowired
//	HttpSession session; //呼び出すクラス
	
	/**
	 *
	 * 
	 * @return
	 */
	
//	@RequestMapping(value = TOP, method = RequestMethod.GET) // 初めて検索画面に来た時
//	public ModelAndView index(ModelAndView mav){
//		List<ThermoInfo> list = schRepository.searchCurDate("1");	//今日の日付で検索、引数はgroup_id
//		mav.setViewName("search");
//		mav.addObject("schList", list);
//		
//		return mav;
//	}
	
	@RequestMapping(value =TOP , method = RequestMethod.GET) // 単一テーブル表示
	public ModelAndView index(ModelAndView mav){
//		List<UserInfo> list = schRepository.findBygroup_id("1");	//今日の日付で検索
		mav.setViewName("test");
		mav.addObject("msg", "sample");
		List<UserInfo> list = schRepository.find();
		mav.addObject("list", list);
		
		return mav;
	}
	
	@RequestMapping(value ="/test" , method = RequestMethod.POST) // 本文詳細ボタンが押されたとき
	public ModelAndView search(@RequestParam("text") String text, ModelAndView mav) {
		mav.setViewName("test");
		List<UserInfo> list = schRepository.findByGender(text);
		mav.addObject("list", list);
		return mav;
	}
	

	
}
