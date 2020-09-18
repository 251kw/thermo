package com.shantery.thermo.editGroupInfo;

import static com.shantery.thermo.util.ThermoConstants.LOGIN_USER;
import static com.shantery.thermo.util.ThermoConstants.TO_EDIT_GROUP_INFO_INP;
import static com.shantery.thermo.util.ThermoConstants.TO_EDIT_GROUP_INFO_COMF;
import static com.shantery.thermo.util.ThermoConstants.TO_EDIT_GROUP_INFO_RES;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.util.ThermoReplaceValue;

/**
 * @author d.ito
 *グループ情報を更新する機能
 */
@Controller
class EditGroupInfoController {
	@Autowired
	private EditGroupInfoService gUpdateService;	
	@Autowired
	HttpSession session;
	@Autowired
	EditGroupInfoRepositoryUser gu_rpository;
	@Autowired
	EditGroupInfoRepository u_rpository;
	
	/**
	 * 検索画面から遷移
	 * 更新情報確認画面へ遷移
	 * @param model
	 * @param groupUpdateForm
	 * @return
	 */
	@RequestMapping(value = "/editGroupInfoInput", method = RequestMethod.GET)
	public String input(Model model, EditGroupInfoForm groupUpdateForm){		
		//ログインユーザーと同じグループIDのユーザー情報受け取る
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		//グループ情報を取得
		List<GroupMstEntity> list = u_rpository.findByGroupId(gu_rpository.findById(loginuser.getUser_id()).get().getGroup_id());
		//グループ情報から要素を取得できるように変換
		//Formに情報をセットする
		groupUpdateForm.setGroupName(list.get(0).getGroup_name());
		groupUpdateForm.setGroupPass(list.get(0).getGroup_pass());
		
		model.addAttribute("groupUpdateForm", groupUpdateForm);
		model.addAttribute("lg_id", list.get(0).getGroup_id());

		//ログインしているグループのグループIDをセッションにセット
		session.setAttribute("loginGroupId", list.get(0).getGroup_id());
		
		return TO_EDIT_GROUP_INFO_INP;//グループ情報更新画面へ遷移"groupUpdateInput"
	}
		
	/**
	 * 確認画面から修正ボタンが押された場合に遷移
	 * @param model
	 * @param gUpEn
	 * @param groupUpdateForm　//入力」情報を保持
	 * @return
	 */
	@RequestMapping(value = "/guInput_return", method = RequestMethod.GET)
	public String input_return( Model model, GroupMstEntity gUpEn, EditGroupInfoForm groupUpdateForm){
		EditGroupInfoForm groupUpdatedata = (EditGroupInfoForm) session.getAttribute("guForm");
		//入力された情報をセットする
		model.addAttribute("groupUpdateForm", groupUpdatedata);
		model.addAttribute("lg_id" , session.getAttribute("loginGroupId"));
		return TO_EDIT_GROUP_INFO_INP;//グループ情報更新画面へ遷移"groupUpdateInput"
	}
	
	/**
	 * 更新入力画面から遷移
	 * @param groupUpdateForm
	 * @param result
	 * @param model
	 * @param bindRes
	 * @return
	 */
	@RequestMapping(value = "/groupUpdateConfirm", method = RequestMethod.POST)
	public String confirm(@Validated @ModelAttribute("groupUpdateForm") EditGroupInfoForm groupUpdateForm, 
			BindingResult result, Model model,BindingResult bindRes) {
			//エラーチェック
			if( bindRes.getAllErrors().isEmpty()) {
				//エラーがない場合
				groupUpdateForm.setGroupName(ThermoReplaceValue.trimBlank(groupUpdateForm.getGroupName())); //Nameの前後の空白を処理
				//情報をセット
				model.addAttribute("groupId" , session.getAttribute("loginGroupId"));
				model.addAttribute("groupPass" , groupUpdateForm.getGroupPass());
				model.addAttribute("groupName" , groupUpdateForm.getGroupName());
				session.setAttribute("guForm", groupUpdateForm);
				
				return TO_EDIT_GROUP_INFO_COMF;//グループ情報入力画面へ遷移"groupInfoInput"
			}else {
				//エラーがあった場合
				model.addAttribute("lg_id" , session.getAttribute("loginGroupId")); //ログイングループIDを渡す
				return TO_EDIT_GROUP_INFO_INP;//グループ情報入力画面へ遷移"groupInfoInput"
			}			
		}
	
	/**
	 * 更新確認画面から遷移
	 * グループデータの更新を行う
	 * @param groupUpdateForm
	 * @param model
	 * @param gUpEn
	 * @return
	 */
	@RequestMapping(value = "/groupUpdateResult", method = RequestMethod.POST)
	public String groupInfoResult(@Validated @ModelAttribute("groupUpdateForm") EditGroupInfoForm groupUpdateForm, 
			Model model,GroupMstEntity gUpEn) {	
		//Formに入っているユーザ情報をEntityに変換
		gUpEn = groupUpdateForm._toConvertGroupInfoEntity();
		gUpdateService.update(gUpEn);//エンティティの情報を更新
		//情報をセット
		model.addAttribute("groupId" , session.getAttribute("loginGroupId"));
		model.addAttribute("groupName" , groupUpdateForm.getGroupName());
		model.addAttribute("groupPass" , groupUpdateForm.getGroupPass());
		
		//sessionオブジェクトのグループ名を変更する
		session.setAttribute("login_group", groupUpdateForm.getGroupName());

		//sessionオブジェクトの"gForm"を削除する
		session.removeAttribute("guForm");
		session.removeAttribute("loginGroupId");
		
		return TO_EDIT_GROUP_INFO_RES;//グループ情報登録完了画面へ遷移"groupInfoResult"
	}
}