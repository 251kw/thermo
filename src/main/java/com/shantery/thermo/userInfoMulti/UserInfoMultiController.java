package com.shantery.thermo.userInfoMulti;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserInfoMultiController {

	/**
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_INPUT_MULTI, method = RequestMethod.GET)
	public String userInfoInput() throws ParseException {
		
		return "a";
	}
	
	@RequestMapping(value = "/userInfoMultiInput", method = RequestMethod.GET)
	public String getCSVfile(@RequestParam(name = USERS_INFO) MultipartFile usersInfo) throws ParseException {
		
		if(usersInfo.isEmpty()) {
			// TODO 異常終了
		}
		
		return "a";
	}
	
}
