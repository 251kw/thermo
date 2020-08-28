package com.shantery.thermo.userInfo;

import java.util.Optional;


import javax.servlet.http.HttpSession;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_GENDER;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_GRADE;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_ADMIN;
import static com.shantery.thermo.util.ThermoConstants.TO_USER_INFO_INP;
import static com.shantery.thermo.util.ThermoConstants.TO_USER_INFO_CONF;
import static com.shantery.thermo.util.ThermoConstants.TO_USER_INFO_RES;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_GR_ER;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_ID_ER;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_AGE_ER;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_GR_ER2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.util.ThermoReplaceValue;
import com.shantery.thermo.util.ThermoUtil;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author h.komatsu
 *ユーザー新規登録機能をまとめたクラス
 */
@Controller
class UserInfoController {
	@Autowired
	private UserInfoService uInfoService; //呼び出すクラス
	@Autowired
	MessageSource messagesource; //messages.propertiesの利用
	@Autowired
	HttpSession session;
	
	/**
	 * 新規ユーザー登録入力画面
	 * @param model
	 * @return 新規登録入力ページ
	 */
	@RequestMapping(value = "/userInfoInput", method = RequestMethod.GET)
	public String input(Model model){
		//もしユーザ情報確認画面から戻ってきた際の処理
		try {
			if(session.getAttribute("uForm").equals(null)) {
				//セッション内データがなければFormをnewする
				model.addAttribute("userInfoForm", new UserInfoForm());
			}else {
				//セッションデータがあれば引き継ぐ
				model.addAttribute("userInfoForm", session.getAttribute("uForm"));
			}
		}catch(Exception e){
			//セッション内データがない場合必ずnullpoになるため、Formをnewする
			model.addAttribute("userInfoForm", new UserInfoForm());
		}
		return TO_USER_INFO_INP;//ユーザ情報入力画面へ遷移"userInfoInput"
	}
			
	
/**
 * 新規ユーザー登録内容確認画面
 * @param userInfoForm 入力されたユーザ情報を保持
 * @param result
 * @param model
 * @param bindRes　入力エラー項目情報
 * @return　エラーがあればuserInfoInputへ、なければuserInfoConfirmへ遷移
 */
@RequestMapping(value = "/userInfoConfirm", method = RequestMethod.POST)
public String confirm(@Validated @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, 
		BindingResult result,Model model,BindingResult bindRes) {
	
	//ユーザID重複調査
	Optional<UserInfoEntity> test2List = uInfoService.getGrDate(userInfoForm.getUserId());
	userInfoForm.setDupUId(test2List);//ユーザID重複調査結果をFormにセット
	//グループ正誤調査
	Optional<GroupMstEntity> grList = uInfoService.getGrInfo(userInfoForm.getGroupId(),userInfoForm.getGroupPass());
	userInfoForm.setErrGPass(grList);//グループ正誤調査結果をFormにセット
	//年齢チェック
	Boolean age = true;//変数ageの初期化
	if(!(userInfoForm.getBirthday().isEmpty())) {
		age = ThermoUtil.ageLimit(userInfoForm.getBirthday());
		}
	
	if(test2List.orElse(null) == null && grList.orElse(null) != null && bindRes.hasErrors() == false && age == true) {
		//ユーザIDが被ってない＆グループIDとグループパスワードが正しい＆入力チェックがエラーじゃないとき
		
		//グループ名の前後の空白を除去し、Formに詰め替える
		userInfoForm.setUserName(ThermoReplaceValue.trimBlank(userInfoForm.getUserName()));
		//Formに入っているユーザー情報を表示用に変換(セレクトボックスのみ)
		UserInfoEntity ulist = userInfoForm._toConvertUserInfoEntity();
		ulist.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, ulist.getGender()));
		ulist.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, ulist.getGrade()));
		ulist.setAdmin_flg(ThermoReplaceValue.valueToName(KBN_TYPE_ADMIN, ulist.getAdmin_flg()));
		//モデルとセッションにForm情報、取得した表示変換後情報をセット
		model.addAttribute("ulist", ulist);
		session.setAttribute("ulist", ulist);
		session.setAttribute("uForm", userInfoForm);
		
		return TO_USER_INFO_CONF;//確認画面に遷移"userInfoConfirm"
		
	}else {
		//既に登録されているユーザIDの場合、エラー文をset
		if(test2List.orElse(null) != null) {
			model.addAttribute("uIdError", USER_INP_ID_ER);
		}else if(grList.orElse(null) == null && !(userInfoForm.getGroupId().isEmpty()) && !(userInfoForm.getGroupPass().isEmpty())) {
			//グループIDがない、もしくはグループパスワードが間違いの場合、エラー文をset
			model.addAttribute("grError", USER_INP_GR_ER);
			model.addAttribute("grError2", USER_INP_GR_ER2);
		}else if(age == false) {
			//年齢が120歳以上の場合、エラー文をset
			model.addAttribute("ageError", USER_INP_AGE_ER);
		}
		//ユーザ情報入力画面に遷移"userInfoInput"
		return TO_USER_INFO_INP;
	}
}
		
	/**
	 * 新規ユーザー登録完了画面
	 * @param uInfoData 入力されたユーザ情報を保持
	 * @param model
	 * @param uInEn FormからEntityに変換したユーザ情報
	 * @return 登録完了画面
	 */
	@RequestMapping(value = "/userInfoResult", method = RequestMethod.POST)
	public String userInfoResult(@Validated @ModelAttribute("userInfoForm") UserInfoForm uInfoData, 
			Model model,UserInfoEntity uInEn) {
		
		//セッション内のulist(表示用に変換したリスト)を取得、モデルにセット
		model.addAttribute("ul", session.getAttribute("ulist"));
		
		//Formに入っているユーザ情報をEntityに変換
		uInEn = uInfoData._toConvertUserInfoEntity();
		//Entityの情報を登録する
		uInfoService.create(uInEn);
		
		//sessionオブジェクトを削除する
		session.removeAttribute("ulist");
		session.removeAttribute("uForm");
		
		return TO_USER_INFO_RES;//ユーザ情報登録完了画面に遷移"userInfoResult"
		
	}
	
}


