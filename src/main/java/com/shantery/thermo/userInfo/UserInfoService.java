package com.shantery.thermo.userInfo;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.groupInfo.GroupInfoRepository;

/**
 * @author h.komatsu
 *新規ユーザー登録のサービスクラス
 */
@Service
public class UserInfoService {
	@Autowired
	UserInfoRepository uInfoRepository; //呼び出すクラス
	@Autowired
	GroupInfoRepository gInfoRepojitory; //呼び出すクラス
	
	/**
	 * 入力されたユーザIDに一致するデータがDBに登録されているか検索
	 * @param user_id	新規登録時入力されたユーザID
	 * @return　検索結果(存在している場合、該当者のデータがリストで返却される)
	 */
	public Optional<UserInfoEntity> getGrDate(String user_id){
		return uInfoRepository.findById(user_id);
	}

	/**
	 * 入力されたグループIDとグループパスワードがDBに登録されているか検索
	 * @param group_Id		新規登録時入力されたグループID
	 * @param group_pass	新規登録時入力されたグループID
	 * @return	検索結果(存在している場合、該当グループのデータがリストで返却される)
	 */
	public Optional<GroupMstEntity> getGrInfo(String group_Id, String group_pass){
		return gInfoRepojitory.findByGroupIdAndGroupPass(group_Id, group_pass);
	}
	
	/**
	 * DBへ新規ユーザー情報登録
	 * @param uInfoData 入力された新規ユーザー情報
	 */
	public void create(UserInfoEntity uInfoData) {		
		uInfoRepository.saveAndFlush(uInfoData);
	}

}
