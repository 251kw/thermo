package com.shantery.thermo;

import static com.shantery.thermo.util.ThermoConstants.*;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.search.SearchInfoForm;
import com.shantery.thermo.search.SearchRepository;
import com.shantery.thermo.search.SearchService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;


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
	
	@Autowired
	private SearchRepository schRepository;
	
	@Autowired
	private SearchService schService;
	
	@Autowired
	HttpSession session; 

	/**
	 * アプリケーションを起動させたとき、もしくはURL直接入力で動く
	 * @return ログイン画面 
	 */
	@RequestMapping(value = TOP, method = RequestMethod.GET)
	public String index(
			@ModelAttribute(THERMO_FORM) 
			ThermoForm FormValue){	// この時点では空
		
		// もしsessionスコープ内にデータがあるなら削除する
		if (session.getAttribute(LOGIN_USER) != null) {
			session.removeAttribute(LOGIN_USER);
		}
		//userInfoのsessionオブジェクトを削除する
		if(session.getAttribute("ulist") != null) {
			session.removeAttribute("ulist");
		}
		//userInfoのsessionオブジェクトを削除する
		if(session.getAttribute("uForm") != null) {
			session.removeAttribute("uForm");
		}
		//groupInfoのsessionオブジェクトを削除する
		if(session.getAttribute("gForm") != null) {
			session.removeAttribute("gForm");
		}
			
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
			@ModelAttribute(THERMO_FORM)
			@Validated
			ThermoForm FormValue,
			BindingResult result,
			UserInfoEntity userinfo,
			Model model){
		
		String logintransition = null;// 遷移先を格納する変数
		Boolean check = null; // 登録されているユーザーかどうか識別するための変数
		String errormessage = null;// エラーメッセージ用の変数
		
		Optional<UserInfoEntity> data = repository.findById(FormValue.getUserId());// 入力されたIDでデータベースを検索
		userinfo = thermoService.checkdata(userinfo,data);// 検索した情報をuserinfo型のオブジェクトに格納
		
		// IDが正常かどうかによって処理を振り分け
		if(userinfo == null) {
			check = false;	// 該当するデータがなかった場合
		}else {
			// 該当するデータがある場合は、パスワードが正常かどうかを確認
			check = thermoService.checkUser(FormValue.getUserpass(),userinfo.getUser_pass(),check);
		}
		
		if(check==true) { // 正常なユーザーの場合
			// ログイン中のユーザーの情報を保持
			session.setAttribute(LOGIN_USER, userinfo);
			session.setAttribute("login_group", userinfo.getUGroupMstEntity().getGroup_name());
			
			List<ThermoInfoEntity> schlist = schRepository.searchCurDate(userinfo.getGroup_id());	//今日の日付で検索	//group_idで絞る
			
			model.addAttribute("searchInfo", new SearchInfoForm());
			model.addAttribute("list", schlist);
			
			boolean display = true;
			if(schlist.size()==0) {
				model.addAttribute("nolist_msg", TODAY_NOLIST_MSG);
				display = false;
			} else if(schlist.size()==MAX_SCH_LISTINT){
				model.addAttribute("overlist_msg", OVER_LIST_MSG);
			}
			
			model.addAttribute("display", display);
			
			int adminbtn = schService.isAdminFlg(userinfo);
			model.addAttribute("adminbtn", adminbtn);
			
			session.setAttribute(SCH_LIST, schlist);
			
		}else {	 // 不正なユーザーの場合
			// エラーメッセージを格納
			errormessage = thermoService.setErrormessage(errormessage);
			model.addAttribute(LOGIN_ERROR, errormessage);
		}
		
		// checkの結果によって遷移先を振り分ける
		logintransition = thermoService.setLoginTransition(check, logintransition);
		
		// ページを移動
		return logintransition;
		
	}
	
}

