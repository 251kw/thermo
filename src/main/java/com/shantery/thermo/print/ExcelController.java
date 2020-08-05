
package com.shantery.thermo.print;
 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.search.SearchEntity;
  
@Controller 
public class ExcelController {
	
	//リポジトリクラス呼出し
//	@Autowired
//	ExcelRepository repository;
//	@Autowired
//	ExcelRepository2 repository2;
	
	@Autowired
	HttpSession session; //呼び出すクラス
	
//	@RequestMapping("/ex")
//	public String exsample(Model model) {
//		model.addAttribute("msg", "sample");
//		List<ThermoEntity> list = repository.findAll();
//		model.addAttribute("list", list);
//		session.setAttribute("samplelist",list);
//		return "extest";
//	}
	
	@RequestMapping("/excel") 
	public  ExcelView excel( ExcelView mav) {
		return mav; 
	} 
}
