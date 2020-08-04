package com.shantery.thermo.groupInfo;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.shantery.thermo.entity.GroupMstEntity;


/**
 * GroupInfoのFromクラス
 */

public class GroupInfoForm implements Serializable {

	
	/** グループID **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 32,message="4文字以上、32文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String groupId;
	/** グループパスワード **/
	@NotBlank(message = "※入力必須項目※　半角英数字で入力してください")
	@Size(min = 4, max = 16,message="4文字以上、16文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9\\-]+",message="記号、スペースは入力できません")
	private String groupPass;
	/** グループ名 **/
	@NotBlank(message = "※入力必須項目※　記号以外で入力してください")
	@Size(min = 1, max = 64,message="1文字以上、64文字以下で入力してください")
	@Pattern(regexp = "[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠]+$",message="記号、スペースは入力できません")
	private String groupName;
	/** 更新時間 **/
	private String updateTime;
	

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getGroupPass() {
		return groupPass;
	}

	public void setGroupPass(String groupPass) {
		this.groupPass = groupPass;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	//Formで受け取った情報をEntityに変換する
	public GroupMstEntity _toConvertGroupInfoEntity(){
		GroupMstEntity gInEn = new GroupMstEntity();
		
		gInEn.setGroup_id(getGroupId());
		gInEn.setGroup_pass(getGroupPass());
		gInEn.setGroup_name(getGroupName());
		gInEn.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	    return gInEn ;
	 }

	
}
