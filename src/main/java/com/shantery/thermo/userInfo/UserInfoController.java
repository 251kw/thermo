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
	 * 新規ユーザー登録入力画面
	 * @param model
	 * @return 新規登録入力ページ
	 */
	@RequestMapping(value = "/userInfoInput", method = RequestMethod.GET)
	public String input(Model model){
		model.addAttribute("userInfoForm", new UserInfoForm());
		return "userInfoInput";
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
	
		if(test2List.orElse(null) == null && grList.orElse(null) != null && bindRes.hasErrors()) {
			//ユーザIDが被ってない＆グループIDとグループパスワードが正しい＆入力がnullや空白じゃない
			//確認画面に遷移
			return "userInfoConfirm";
			
		}else {
			//既に登録されているユーザIDの場合、エラー文をset
			if(test2List.orElse(null) != null) {
				model.addAttribute("uIdError", "既に登録されているユーザIDです");
			}else if(grList.orElse(null) == null) {
				//グループIDがない、もしくはグループパスワードが間違いの場合、エラー文をset
				model.addAttribute("grError", "グループIDが存在しないか、グループパスワードが間違っています");
			}	
			//入力画面に遷移
			return "userInfoInput";
		}
	}
	
	/**
	 * 新規ユーザー入力確認画面から戻るボタンで戻った時
	 * @param userInfoForm　入力されたユーザ情報を保持
	 * @param result
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/userInfoInput", method = RequestMethod.POST)
	public String returnUserInfoInput(@Validated @ModelAttribute("userInfoForm") UserInfoForm userInfoForm, 
			BindingResult result, Model model) {
		
		return "userInfoInput";	
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
		
		//Formに入っているユーザ情報をEntityに変換
		uInEn = uInfoData._toConvertUserInfoEntity();
		//Entityの情報を登録する
		uInfoService.create(uInEn);
		
		return "userInfoResult";
		
	}
	
}


