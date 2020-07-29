package com.shantery.thermo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="group_mst")
public class Group_mstEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(length=32)
	private String group_id;
	
	@Column(length=64)
	private String group_name;
	
	@Column(length=16)
	private String group_pass;
	
	@Column
	private String update_time;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	public String getGroup_pass() {
		return group_pass;
	}

	public void setGroup_pass(String group_pass) {
		this.group_pass = group_pass;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
}