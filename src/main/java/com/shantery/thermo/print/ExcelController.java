package com.shantery.thermo.print;
 

import javax.servlet.http.HttpSession;

import static com.shantery.thermo.util.ThermoConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author d.ito
 *
 */
@Controller 
public class ExcelController {
	@Autowired
	HttpSession session; //呼び出すクラス
	
	/**
	 * @param mav
	 * excel情報の出力を行う
	 * search.htmlの"印刷"ボタンから遷移
	 * @return
	 */
	@RequestMapping(EXCEL_INFO) 
	public  ExcelView excel(ExcelView mav) {
			return mav; 
	} 
}
