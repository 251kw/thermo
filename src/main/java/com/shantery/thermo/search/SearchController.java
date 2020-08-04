package com.shantery.thermo.search;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

//import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
//	
//	@PersistenceContext
//	EntityManager	em;
//	
	/**
	 *
	 * 
	 * @return
	 */
	
	@RequestMapping(value =TOP , method = RequestMethod.GET) //初めて検索画面に来た時
	public ModelAndView index(ModelAndView mav){
		mav.setViewName("test");
		List<SearchEntity> list = schRepository.searchCurDate("2");	//今日の日付で検索	//group_idで絞る
		mav.addObject("list", list);
		
		return mav;
	}
	
//	@RequestMapping(value ="/test" , method = RequestMethod.POST) // 検索ボタンが押されたとき
//	public ModelAndView search(@RequestParam("text") String text, ModelAndView mav) {
//		mav.setViewName("test");
//		List<UserInfo> list = schRepository.findByGroupId(text);
//		mav.addObject("list", list);
//		return mav;
//	}
	
//	@PostConstruct
//	private void init() {
//		dao = new DaoImpl(em);
//	}

	
}
