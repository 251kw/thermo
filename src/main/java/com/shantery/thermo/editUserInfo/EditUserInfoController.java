package com.shantery.thermo.editUserInfo;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_GENDER;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_GRADE;
import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import static com.shantery.thermo.util.ThermoConstants.KBN_TYPE_ADMIN;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_GR_ER;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_AGE_ER;
import static com.shantery.thermo.util.ThermoConstants.USER_INP_GR_ID_ER;

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
import com.shantery.thermo.userInfo.UserInfoForm;
import com.shantery.thermo.util.ThermoReplaceValue;
import com.shantery.thermo.util.ThermoUtil;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author h.komatsu
 *ユーザー情報更新機能をまとめたクラス
 */
@Controller
class EditUserInfoController {
	@Autowired
	private EditUserInfoService eUService; //呼び出すクラス
	@Autowired
	private EditUserInfoGroupRepository eUgRepository; //呼び出すクラス
	@Autowired
	MessageSource messagesource; //messages.propertiesの利用
	@Autowired
	HttpSession session;

	/**
	 * ユーザー情報更新画面
	 * @param model
	 * @return ユーザー情報更新入力画面
	 */
	@RequestMapping(value = "/editUserInfoInput", method = RequestMethod.GET)
	public String input(Model model,UserInfoForm uInFm){
		try {
			if(!(session.getAttribute("uForm").equals(null))) {	 
				//セッションデータがあれば引き継ぐ
				model.addAttribute("userInfoForm", session.getAttribute("uForm"));
			}
		} catch(Exception e){
			//セッション内データがない場合必ずnullになる
			
			//ログインユーザー情報の取得
			UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
			//ユーザーID使用してDBからユーザー情報再取得
			UserInfoEntity date = eUService.checkdata(loginuser, eUService.getGrDate(loginuser.getUser_id()));
			//EntityからFormへ詰め替え
			uInFm = date._toConvertUserInfoForm();
			//グループ情報の取得
			GroupMstEntity loginGr = eUService.getGrPass(loginuser.getGroup_id());
			//グループパスワードをFormへ追加
			uInFm.setGroupPass(loginGr.getGroup_pass());
			//モデルへセット
			model.addAttribute("userInfoForm", uInFm);
		}
		return "editUserInfoInput";//ユーザー情報更新入力画面へ遷移
	}	
	
	
	/**
	 * ユーザー情報更新内容確認画面
	 * @param userInfoForm　ユーザー情報
	 * @param model 入力情報一時保持
	 * @param bindRes　入力チェックエラー
	 * @return　エラーがあれば入力画面へ、なければ確認画面へ遷移
	 */
	@RequestMapping(value = "/editUserInfoConfirm", method = RequestMethod.POST)
	public String confirm(@Validated @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, 
			BindingResult result,Model model,BindingResult bindRes) {
		//ログインユーザー情報の取得
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		//グループ存在チェック
		Optional<GroupMstEntity> grId = eUService.getGrId(userInfoForm.getGroupId());
		//グループ正誤調査
		Optional<GroupMstEntity> grList = eUService.getGrInfo(userInfoForm.getGroupId(),userInfoForm.getGroupPass());
		userInfoForm.setErrGPass(grList);//グループ正誤調査結果をFormにセット
		//年齢チェック
		Boolean age = true;//変数ageの初期化
		if(!(userInfoForm.getBirthday().isEmpty())) {
			age = ThermoUtil.ageLimit(userInfoForm.getBirthday());
			}
		Boolean admin = true;//変数adminの初期化
		if(loginuser.getAdmin_flg().equals("0") && userInfoForm.getAdminFlg().equals("1")) {
			admin = false;
		}
		if(grList.orElse(null) != null && bindRes.hasErrors() == false && age == true && admin == true) {
			//グループIDとグループパスワードが正しい＆入力チェックがエラーじゃないとき
			
			//氏名の前後の空白を除去し、Formに詰め替える
			userInfoForm.setUserName(ThermoReplaceValue.trimBlank(userInfoForm.getUserName()));
			//Formに入っているユーザー情報を表示用に変換(セレクトボックスのみ)
			UserInfoEntity uslist = userInfoForm._toConvertUserInfoEntity();
			uslist.setGender(ThermoReplaceValue.valueToName(KBN_TYPE_GENDER, uslist.getGender()));
			uslist.setGrade(ThermoReplaceValue.valueToName(KBN_TYPE_GRADE, uslist.getGrade()));
			uslist.setAdmin_flg(ThermoReplaceValue.valueToName(KBN_TYPE_ADMIN, uslist.getAdmin_flg()));
			//モデルとセッションにForm情報、取得した表示変換後情報をセット
			model.addAttribute("uslist", uslist);
			session.setAttribute("uslist", uslist);
			session.setAttribute("uForm", userInfoForm);
			
			return "editUserInfoConfirm";//更新確認画面に遷移
			
		}else {
			if(!(userInfoForm.getGroupId().isEmpty()) && !(grId.isPresent())){
				//グループIDがDBに存在していない場合、エラー文をセット
				model.addAttribute("grIdError", USER_INP_GR_ID_ER);
			}else if(grList.orElse(null) == null && !(userInfoForm.getGroupId().isEmpty()) && !(userInfoForm.getGroupPass().isEmpty())) {
				//グループパスワードが間違っている場合、エラー文をset
				model.addAttribute("grError", USER_INP_GR_ER);
			}else if(age == false) {
				//年齢が120歳以上の場合、エラー文をset
				model.addAttribute("ageError", USER_INP_AGE_ER);
			}else if(admin == false) {
				model.addAttribute("adminError", "※管理者以外は変更できません");
			}
			//ユーザー情報更新入力画面に遷移"
			return "editUserInfoInput";
		}
	}
		
	
	/**
	 * ユーザー情報更新完了画面
	 * @param uInfoAddData　ユーザー更新情報
	 * @param model　入力情報一時保持
	 * @param uInEn　ユーザーエンティティクラス
	 * @return 登録完了画面
	 */
	@RequestMapping(value = "/editUserInfoResult", method = RequestMethod.POST)
	public String userInfoResult(@Validated @ModelAttribute("userInfoAddForm") UserInfoForm uInfoAddData, 
			Model model,UserInfoEntity uInEn) {
		//セッション内のuslist(表示用に変換したリスト)を取得、モデルにセット
		model.addAttribute("ul", session.getAttribute("uslist"));
		
		//Formに入っているユーザ情報をEntityに変換
		uInEn = uInfoAddData._toConvertUserInfoEntity();
		//Entityの情報を更新する		
		eUService.update(uInEn);
		
		//T更新後、sessionのログイン情報新しく書き換える
		session.setAttribute(LOGIN_USER, uInEn);
		
		//sessionオブジェクトのグループ名を変更する
		session.setAttribute("login_group", eUgRepository.findById(uInEn.getGroup_id()).get().getGroup_name());
		
		//sessionオブジェクトを削除する
		session.removeAttribute("uslist");
		session.removeAttribute("uForm");
		
		return "editUserInfoResult";//ユーザ情報更新完了画面に遷移
		
	}
	
}
