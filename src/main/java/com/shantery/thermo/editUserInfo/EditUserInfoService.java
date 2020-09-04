package com.shantery.thermo.editUserInfo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author h.komatsu
 *ユーザー情報変更のサービスクラス
 */
@Service
class EditUserInfoService {
	@Autowired
	EditUserInfoRepository uInfoRepository; //呼び出すクラス
	@Autowired
	EditGrInfoRepository gInfoRepojitory; //呼び出すクラス
		
	/**
	 * 入力されたユーザIDに一致するデータがDBに登録されているか検索
	 * @param user_id	ユーザID
	 * @return　検索結果(存在している場合、該当者のデータがリストで返却される)
	 */
	public Optional<UserInfoEntity> getGrDate(String user_id){
		return uInfoRepository.findById(user_id);
	}
	
	/**
	 * 入力されたグループIDとグループパスワードがDBに登録されているか検索
	 * @param group_Id		グループID
	 * @param group_pass	グループパスワード
	 * @return	検索結果(存在している場合、該当グループのデータがリストで返却される)
	 */
	public Optional<GroupMstEntity> getGrInfo(String group_Id, String group_pass){
		return gInfoRepojitory.findByGroupIdAndGroupPass(group_Id, group_pass);
	}
	
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
	
	/**
	 * Optional型のデータをUserInfoEntity型のオブジェクトに入れなおすメソッド
	 * @param userinfo データを移し替えるオブジェクト
	 * @param data 入力されたuserIDを元に検索した結果を保持しているOptional型のオブジェクト
	 * @return データをもらったUserInfoEntity型のオブジェクト
	 */
	public UserInfoEntity checkdata(UserInfoEntity userinfo,Optional<UserInfoEntity> data) {
		
		if(data.isPresent()==true) {	// dataがデータを持っている場合
			// 値を取得するためにEntityに格納しなおす
			userinfo = data.get();
		}else {		// dataがデータを持っていない場合
			userinfo = null;
		}
		return userinfo;
	}

}
