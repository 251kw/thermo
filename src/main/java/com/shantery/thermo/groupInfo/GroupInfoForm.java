package com.shantery.thermo.groupInfo;

import java.text.SimpleDateFormat;


import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.shantery.thermo.entity.GroupMstEntity;
import static com.shantery.thermo.util.ThermoConstants.NAME_PATTERN;
import static com.shantery.thermo.util.ThermoConstants.THERMO_REGEX_PATTERN;

/**
 * @author h.komatsu
 * GroupInfoのFromクラス
 */

public class GroupInfoForm{

	
	/** グループID **/
	@NotBlank
	@Size(min = 4, max = 32)
	@Pattern(regexp = THERMO_REGEX_PATTERN)
	private String groupId;
	/** グループパスワード **/
	@NotBlank
	@Size(min = 4, max = 16)
	@Pattern(regexp = THERMO_REGEX_PATTERN)
	private String groupPass;
	/** グループ名 **/
	@NotBlank
	@Size(min = 1, max = 64)
	@Pattern(regexp = NAME_PATTERN)
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
