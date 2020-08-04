package com.shantery.thermo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="group_mst")
public class GroupMstEntity {

	@Id
	@Column(length=32,name="group_id")
	private String groupId;
	
	@Column(length=64,name="group_name")
	private String groupName;
	
	@Column(length=16,name="group_pass")
	private String groupPass;
	
	@Column(name="update_time")
	private String updateTime;

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