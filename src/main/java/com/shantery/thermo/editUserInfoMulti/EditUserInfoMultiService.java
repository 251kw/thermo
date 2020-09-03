package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.UserInfoEntity;

@Service
public class EditUserInfoMultiService {

	@Autowired
	EditUserInfoMultiRepository repository;

		public ArrayList<UserInfoEntity> storeOriginalInfo(String groupid,UserInfoEntity userinfo,ArrayList<UserInfoEntity> original) {
			
			original = repository.findByGroupId(groupid);
			
			return original;
		}
}
