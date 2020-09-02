package com.shantery.thermo.editUserInfo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.groupInfo.GroupInfoRepository;
import com.shantery.thermo.userInfo.UserInfoRepository;

/**
 * @author h.komatsu
 *ユーザー情報変更のサービスクラス
 */
@Service
public class EditUserInfoService {
	@Autowired
	UserInfoRepository uInfoRepository; //呼び出すクラス
	@Autowired
	GroupInfoRepository gInfoRepojitory; //呼び出すクラス
		
	
	/**
	 * GroupMstEntity型でGroupIdからグループ情報を取得
	 * @param group_Id　グループID
	 * @return　グループ情報
	 */
	public GroupMstEntity getGrPass(String group_Id){		
		return gInfoRepojitory.findByGroupId(group_Id);
	}
	
	/**
	 * DB内のユーザー情報更新
	 * @param uInfoData ユーザー更新情報
	 */
	public void update(UserInfoEntity uInfoData) {		
		uInfoRepository.saveAndFlush(uInfoData);
	}
	
	

}
