package com.shantery.thermo;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shantery.thermo.entity.UserInfoEntity;

import org.springframework.ui.Model;


/**
 * @author k.takahashi
 * ログイン画面で想定される処理を記述したクラス
 */
@Controller
class ThermoController {

	@Autowired
	private ThermoService thermoService;
	
	@Autowired
	ThermoRepository repository;

	/**
	 * アプリケーションを起動させたとき、もしくはURL直接入力で動く
	 * @return ログイン画面 
	 */
	@RequestMapping(value = TOP, method = RequestMethod.GET)
	public String index(){
		// トップページへ移動
		return TO_TOP;
	}
	
	/**
	 * ログイン画面のログインボタンを押された時に動く
	 * @param userId
	 * @param userpass
	 * @param model 入力された値を保持する
	 * @return 遷移先
	 */
	@RequestMapping(value = LOGIN, method = RequestMethod.POST)
	public String checkUser(
			// パラメータを受け取る
			@RequestParam(value="userId")String userId,
			@RequestParam(value="userpass")String userpass,
			UserInfoEntity userinfo,
			Model model){
		
		// 値を保持するための処理
		model.addAttribute("userId", userId);
		model.addAttribute("userpass", userpass);
		
		String logintransition = null;// 遷移先を格納する変数
		Boolean check = null; // 登録されているユーザーかどうか識別するための変数
		String errormessage = null;// エラーメッセージ用の変数
		
		Optional<UserInfoEntity> data = repository.findById(userId);// 入力されたIDでデータベースを検索
		userinfo = thermoService.checkdata(userinfo,data);// 検索した情報をuserinfo型のオブジェクトに格納
		
		// IDが正常かどうかによって処理を振り分け
		if(userinfo == null) {
			check = false;	// 該当するデータがなかった場合
		}else {
			// 該当するデータがある場合は、パスワードが正常かどうかを確認
			check = thermoService.checkUser(userpass,userinfo.getUser_pass(),check);
		}
		
		// checkの結果によって遷移先を振り分ける
		logintransition = thermoService.setLoginTransition(check, logintransition);
		
		// メッセージを格納
		errormessage = thermoService.setErrormessage(errormessage, check);
		if(errormessage != null) {
			model.addAttribute("error", errormessage);
		}
		
		// ページを移動
		return logintransition;
	}
	
	/**
	 * 新規登録ボタンを押された時に動く
	 * @param registoption ログイン画面のselectで指定された値
	 * @return
	 */
	@RequestMapping(value = "/registoption", method = RequestMethod.POST)
	public String checkOption(@RequestParam(value="regist")String registoption,
			Model model){	// パラメータを受け取る
		
		String registtransition = null;// 遷移先を格納する変数
		
		// パラメータによって遷移先を格納
		registtransition = thermoService.setRegistTransition(registoption, registtransition);

		// 遷移先を格納
		model.addAttribute("option", registoption);
		
		// ページを移動
		return registtransition;
	}
	
}

