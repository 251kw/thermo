package com.shantery.thermo.userInfo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author h.komatsu
 *ユーザー新規登録機能をまとめたクラス
 */
@Controller
class UserInfoController {
	@Autowired
	private UserInfoService uInfoService; //呼び出すクラス
	
	/**
	 * 新規登録入力画面
	 * @param model
	 * @return 新規登録入力ページ
	 */
	@RequestMapping(value = "/userInfoInput", method = RequestMethod.GET)
	public String input(Model model){
		model.addAttribute("userInfoForm", new UserInfoForm());
		return "userInfoInput";
	}
	
			
	/**
	 * 登録内容確認画面
	 * @param userInfoForm 入力されたユーザ情報を保持
	 * @param result
	 * @param model
	 * @param bindRes　入力エラー項目情報
	 * @return　エラーがあればuserInfoInput.html、エラーがなければuserInfoConfirm.html
	 */
	@RequestMapping(value = "/userInfoConfirm", method = RequestMethod.POST)
	public String confirm(@Validated @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, 
			BindingResult result,Model model,BindingResult bindRes) {
		
		//入力されたユーザIDと同一のユーザIDないか調査
		Optional<UserInfoEntity> test2List = uInfoService.getGrDate(userInfoForm.getUserId());
		//入力されたGroupIDとGroupPassが合っているか調査
		Optional<GroupMstEntity> grList = uInfoService.getGrInfo(userInfoForm.getGroupId(),userInfoForm.getGroupPass());//TODO　ANDでできなかった
		
		if(test2List.orElse(null) == null && grList.orElse(null) != null && bindRes.getAllErrors().isEmpty()) {
			//入力エラー、重複ユーザID、グループIDとグループパスが正常であればif文を抜ける
			
		}else {
			
			//既に登録されているユーザIDの場合、エラー文をset
			if(test2List.orElse(null) != null) {
				model.addAttribute("uIdError", "既に登録されているユーザIDです");
			}else if(grList.orElse(null) == null && userInfoForm.getUserId() != null) {
				//グループIDがない、もしくはグループパスワードが間違いの場合、エラー文をset
				model.addAttribute("grError", "グループIDが存在しないか、グループパスワードが間違っています");
			}
			
			return "userInfoInput";
		}
		
		return "userInfoConfirm";
	}
	
	
	//入力確認画面から戻るボタンで戻った時
	@RequestMapping(value = "/userInfoInput", method = RequestMethod.POST)
	public String returnUserInfoInput(@Validated @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, 
			BindingResult result, Model model) {
		
		return "userInfoInput";	
	}
	
	
	//登録完了画面
	@RequestMapping(value = "/userInfoResult", method = RequestMethod.POST)
	public String userInfoResult(@Validated @ModelAttribute("userInfoForm") UserInfoForm uInfoData, 
			Model model,UserInfoEntity uInEn) {
		
		
		//Formに入っているユーザ情報をEntityに変換
		uInEn = uInfoData._toConvertUserInfoEntity();
		//Entityの情報を登録する
		uInfoService.create(uInEn);
		
		return "userInfoResult";
		
	}

	/*
	public static List<String> getGradeList(){
		List<String> gradeList = new ArrayList<>();
		gradeList.add(DEFAULT_PEGE);
		return gradeList;
	}
	*/
	
}


