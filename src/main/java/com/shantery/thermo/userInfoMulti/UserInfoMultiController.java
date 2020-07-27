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
	 * ユーザーを一括登録するファイルをアップロードする画面に遷移させる
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_SET, method = RequestMethod.GET)
	public String userInfoInput() throws ParseException {
		
		return TO_USERS_MULTI_CONF;
	}
	
	/**
	 * アップロードしたファイルを受けとり、解析したのち、
	 * エラーがなければ確認画面へ、エラーがあればアップロード画面へ戻す
	 * @param usersInfo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_GET , method = RequestMethod.GET)
	public String getCSVfile(@RequestParam(name = USERS_INFO) MultipartFile usersInfo) throws ParseException {
		
		if(usersInfo.isEmpty()) {
			// TODO 異常終了
			return TO_USERS_MULTI_INP; 
		}
		
		return TO_USERS_MULTI_RES ;
	}
	
	/**
	 * 確認画面で確定を押された時に、データベースに追加して、ログイン画面に戻す（もしくはログイン）
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = USERS_MULTI_CONF_SUC, method = RequestMethod.GET)
	public String userInfoConfirm() throws ParseException {
		
		return "" ; //TODO けいすけ に聞く
	}
	
}
