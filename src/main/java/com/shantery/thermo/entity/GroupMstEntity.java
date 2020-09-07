package com.shantery.thermo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * @author k.takahashi
 * group_mstテーブル用のエンティティ
 */
@Entity
@Table(name="group_mst")
public class GroupMstEntity {

	@Id
	/** グループID **/
	@Column(length=32,name="group_id")
	private String groupId;
	/** グループ名 **/
	@Column(length=64,name="group_name")
	private String groupName;
	/** グループパスワード **/
	@Column(length=16,name="group_pass")
	private String groupPass;
	/** 登録時間 **/
	@Column(name="update_time")
	private String updateTime;

	/** UserInfoEntity **/
	@OneToMany					//↓SQLのINSERT文,UPDATE文に含むかどうか指定
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private List<UserInfoEntity> userInfoEntity;
	
	public List<UserInfoEntity> getUserInfoEntity() {
		return userInfoEntity;
	}

	public void setUserInfoEntity(List<UserInfoEntity> userInfoEntity) {
		this.userInfoEntity = userInfoEntity;
	}
	
	// getter・setter
	public String getGroup_id() {
		return groupId;
	}

	public void setGroup_id(String group_id) {
		this.groupId = group_id;
	}

	public String getGroup_name() {
		return groupName;
	}

	public void setGroup_name(String group_name) {
		this.groupName = group_name;
	}

	public String getGroup_pass() {
		return groupPass;
	}

	public void setGroup_pass(String group_pass) {
		this.groupPass = group_pass;
	}

	public String getUpdate_time() {
		return updateTime;
	}

	public void setUpdate_time(String update_time) {
		this.updateTime = update_time;
	}
	
	
}