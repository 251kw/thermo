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
import com.shantery.thermo.thermoInput.ThermoInputService;

import antlr.collections.List;

@Controller
class ThermoInputController {
	
	@Autowired
	HttpSession session;
	@Autowired
	ThermoInputService service;
	@Autowired
	ThermoInputRepository repository;
	
	/**
	 * テスト
	 * @param mav
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mav) {
		Iterable<ThermoInfoEntity> list = repository.findAll();
		mav.addObject("date",list);
		return mav;
	}

	/**
	 * 検温情報入力ボタンから入力画面へ
	 * @param page
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/from_search"/*FROM_SEARCH_BUTTON*/ , method = RequestMethod.GET)
	public String Input() {
		/*検索画面 からくる　多分佐藤が作る*/
		/*model にグループIdが同じの人をもらう*/
		return "thermoInput"/*TO_INPUT*/;
	}
	
	/**
	 * 検温情報入力から確認画面へ
	 * @param page
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/from_input"/*FROM_INPUT_BUTTON*/ , method = RequestMethod.POST)
	public String InputConfirm(@ModelAttribute("thForm") ThermoInputForm form, Model model) {
		
		model.addAttribute("list", form.gettList());
		session.setAttribute("list", form.gettList());
		//サービスで入力チェックメソッド入れる,エラーを出す
		String msg = null;
		
		if(msg != null) {
			model.addAttribute("msg",msg);
			return "thermoInput";
		}
		
		return "thermoConfirm"/*TO_INPUT_CONFIRM*/;
	}
	
	/**
	 * 検温情報確認画面から結果画面へ
	 * @param page
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value =  "/from_confirm"/*FROM_INPUT_CONFIRM_BUTTON*/ , method = RequestMethod.POST)
	public String InputResult() {
		ArrayList<ThermoInputForm.Detail> list = (ArrayList<ThermoInputForm.Detail>)session.getAttribute("list");
		
		service.registAll(list);
		
		
		return "thermoResult"/*TO_INPUT_RESULT*/;
	}
	
	/**
	 * 登録情報入力画面へ戻る
	 * @param page
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/return_input/"/*FROM_RETURN_INPUT_BUTTON*/ , method = RequestMethod.GET)
	public String InputReturn() {
		
		
		return "thermoInput" /*TO_INPUT*/;
	}
	
	/**
	 * 検索画面へ戻る
	 * @param page
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/return_search/"/*FROM_RETURN_SEARCH_BUTTON*/ , method = RequestMethod.POST)
	public String SearchReturn() {
		
		
		return "search"/*TO_SEARCH*/;
	}
	
}
