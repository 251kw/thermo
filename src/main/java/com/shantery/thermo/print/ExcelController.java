package com.shantery.thermo.print;
 
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shantery.thermo.entity.ThermoInfoEntity;

@Controller 
public class ExcelController {
	
	//リポジトリクラス呼出し
//	@Autowired
//	ExcelRepository repository;
	
	@Autowired
	HttpSession session; //呼び出すクラス
	
//	@RequestMapping("/excel")
//	public String exsample(Model model) {
//		model.addAttribute("msg", "sample");
//		List<ThermoInfoEntity> list = repository.findAll();
//		model.addAttribute("list", list);
//		session.setAttribute("samplelist",list);
//
//		return "extest";
//	}
	
	@RequestMapping("/ex") 
	public  ExcelView excel(ExcelView mav) {
		return mav; 
	} 
}
