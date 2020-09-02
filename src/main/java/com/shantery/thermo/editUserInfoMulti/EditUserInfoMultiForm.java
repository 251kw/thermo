package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;


import com.shantery.thermo.entity.UserInfoEntity;



public class EditUserInfoMultiForm {

	
	private ArrayList<UserInfoEntity> userList;
	
	public ArrayList<UserInfoEntity> gettList() {
		return userList;
	}

	public void settList(ArrayList<UserInfoEntity> userList) {
		this.userList = userList;
	}

}
