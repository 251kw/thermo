package com.shantery.thermo.groupInfo;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;

@Service
class GroupInfoService {
	@Autowired
	GroupInfoRepository gInfoRepository; //呼び出すクラス
	
	//入力されたgrIdに一致するデータがDBにあるか調べる
	public Optional<GroupMstEntity> getGrDate(String group_id){
		return gInfoRepository.findById(group_id);
		
	}
	
	//DBのなかみを全部取ってくる
	public List<GroupMstEntity> getUserDate(){
		return gInfoRepository.findAll();
	}
	
	//入力情報をDBに登録
	public void create(GroupMstEntity gInfoData) {
		
		gInfoRepository.saveAndFlush(gInfoData);//Entityに登録
	}

	
}
