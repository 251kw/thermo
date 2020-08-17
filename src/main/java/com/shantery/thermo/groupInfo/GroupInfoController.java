package com.shantery.thermo.groupInfo;

/*
 * import static com.shantery.result2.util.Result2Constants.*;

 */

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.shantery.thermo.entity.GroupMstEntity;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author h.komatsu
 *グループ新規登録機能をまとめたクラス
 */
@Controller
class GroupInfoController {
	@Autowired
	private GroupInfoService gInfoService; //呼び出すクラス
	
	/**
	 * グループ新規登録入力画面
	 * @param model
	 * @return グループ新規登録入力ページ
	 */
	@RequestMapping(value = "/groupInfoInput", method = RequestMethod.GET)
	public String input(Model model){
		model.addAttribute("groupInfoForm", new GroupInfoForm());
		return "groupInfoInput";
	}
	
			
	/**
	 * 新規グループ登録内容確認画面
	 * @param groupInfoForm　入力されたグループ情報を保持
	 * @param result
	 * @param model
	 * @param bindRes	入力エラー項目情報
	 * @return　エラーがあればgroupInfoInputへ、なければgroupInfoConfirmへ遷移
	 */
	@RequestMapping(value = "/groupInfoConfirm", method = RequestMethod.POST)
	public String confirm(@Validated @ModelAttribute("groupInfoForm") GroupInfoForm groupInfoForm, 
			BindingResult result, Model model,BindingResult bindRes) {
		
		//入力されたグループIDと同一グループIDないか調査
		Optional<GroupMstEntity> grList = gInfoService.getGrDate(groupInfoForm.getGroupId());
		
		if(grList.orElse(null) == null && bindRes.getAllErrors().isEmpty()) {
			//グループIDがないとき抜ける
			
		}else {
			//既に登録されているユーザIDの場合、エラー文をset
			if(grList.orElse(null) != null) {
				model.addAttribute("uGrError", "既に登録されているグループIDです");
			}	
			return "groupInfoInput";
		}
		return "groupInfoConfirm";
	}
	
	/**
	 * 入力確認画面から戻るボタンで戻った時
	 * @param groupInfoForm 入力されたユーザ情報を保持
	 * @param result
	 * @param model
	 * @return 入力画面
	 */
	@RequestMapping(value = "/groupInfoInput", method = RequestMethod.POST)
	public String returnGroupInfoInput(@Validated @ModelAttribute("groupInfoForm") GroupInfoForm groupInfoForm, 
			BindingResult result, Model model) {
		
		return "groupInfoInput";
	}
	
	/**
	 * 新規グループ登録完了画面
	 * @param gInfoData 入力されたグループ情報を保持
	 * @param model
	 * @param gInEn　 FormからEntityに変換したユーザ情報
	 * @return　登録完了画面
	 */
	@RequestMapping(value = "/groupInfoResult", method = RequestMethod.POST)
	public String groupInfoResult(@Validated @ModelAttribute("groupInfoForm") GroupInfoForm gInfoData, 
			Model model,GroupMstEntity gInEn) {
		
		gInEn = gInfoData._toConvertGroupInfoEntity();//Formに入っているユーザ情報をEntityに変換
		gInfoService.create(gInEn);//Entityの情報を登録する
		
		return "groupInfoResult";
		
	}

	
}


