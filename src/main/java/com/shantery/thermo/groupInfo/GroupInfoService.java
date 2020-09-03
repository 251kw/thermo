package com.shantery.thermo.groupInfo;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author h.komatsu
 *グループ新規登録のサービスクラス
 */
@Service
class GroupInfoService {
	@Autowired
	GroupInfoRepository gInfoRepository; //呼び出すクラス
	
	/**
	 * 入力されたグループIDに一致するデータがDBにあるか検索
	 * @param group_id
	 * @return　検索結果(存在している場合、該当グループのデータがリストで返却される)
	 */
	public Optional<GroupMstEntity> getGrDate(String group_id){
		return gInfoRepository.findById(group_id);
		
	}
	
	/**
	 * DBへ新規グループ情報登録
	 * @param gInfoData 入力された新規グループ情報
	 */
	public void create(GroupMstEntity gInfoData) {	
		gInfoRepository.saveAndFlush(gInfoData);
	}
	
}
