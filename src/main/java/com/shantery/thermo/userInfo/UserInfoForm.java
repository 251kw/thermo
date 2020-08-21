package com.shantery.thermo.userInfo;

import static com.shantery.thermo.util.ThermoConstants.THERMO_REGEX_PATTERN;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author h.komatsu
 * UserInfoのFromクラス
 */
public class UserInfoForm{
	
	/** グループID **/
	@NotBlank
	@Size(min = 4, max = 32)
	@Pattern(regexp=THERMO_REGEX_PATTERN)
	private String groupId;
	
	/** グループパスワード **/
	@NotBlank
	@Size(min = 4, max = 16)
	@Pattern(regexp=THERMO_REGEX_PATTERN)
	private String groupPass;
	
	/** ユーザID **/
	@NotBlank
	@Size(min = 4, max = 32)
	@Pattern(regexp=THERMO_REGEX_PATTERN)
	private String userId;
	
	/** ユーザーパスワード **/
	@NotBlank
	@Size(min = 4, max = 16)
	@Pattern(regexp=THERMO_REGEX_PATTERN)
	private String userPass;
	
	/** 氏名 **/
	@NotBlank
	@Size(min = 1, max = 64)
	@Pattern(regexp = "[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠 　]+$")//TODO　外部化
	private String userName;
	
	/** 性別 **/
	@NotBlank
	private String gender;
	/** 生年月日 **/
	@NotBlank
	private String birthday;
	/** 学年区分 **/
	@NotBlank
	private String grade;
	/** 管理者フラグ **/
	@NotBlank
	private String adminFlg;
	/** 更新時間 **/
	private String updateTime;
	
	/** グループ正誤チェック **/
	//@Value("first")
	private Optional<GroupMstEntity> errGPass;
	//@Value("#{null}")
	/** ユーザID重複チェック **/
	private Optional<UserInfoEntity> dupUId;
	
	public Optional<UserInfoEntity> getDupUId() {
		return dupUId;
	}

	public void setDupUId(Optional<UserInfoEntity> dupUId) {
		this.dupUId = dupUId;
	}

	public Optional<GroupMstEntity> getErrGPass() {
		return errGPass;
	}

	public void setErrGPass(Optional<GroupMstEntity> errGPass) {
		this.errGPass = errGPass;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGroupPass() {
		return groupPass;
	}

	public void setGroupPass(String groupPass) {
		this.groupPass = groupPass;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAdminFlg() {
		return adminFlg;
	}

	public void setAdminFlg(String adminFlg) {
		this.adminFlg = adminFlg;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	
	//Formで受け取った情報をEntityに変換する
	public UserInfoEntity _toConvertUserInfoEntity(){
		UserInfoEntity uInEn = new UserInfoEntity();
		
		uInEn.setGroup_id(getGroupId());
		uInEn.setUser_id(getUserId());
		uInEn.setUser_pass(getUserPass());
		uInEn.setUser_name(getUserName());
		uInEn.setGender(getGender());
		uInEn.setBirthday(getBirthday());
		uInEn.setGrade(getGrade());
		uInEn.setAdmin_flg(getAdminFlg());
		uInEn.setUpdate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	    return uInEn ;
	 }
	
}
