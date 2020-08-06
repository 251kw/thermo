package com.shantery.thermo.userInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;
import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.groupInfo.GroupInfoRepository;

@Service
class UserInfoService {
	@Autowired
	UserInfoRepository uInfoRepository; //呼び出すクラス
	
	@Autowired
	GroupInfoRepository gInfoRepojitory; //呼び出すクラス


	
	//入力されたgrId,uIdに一致するデータがDBにあるか調べる
	public Optional<ThermoInfoEntity> getGrDate(String user_id){
		return uInfoRepository.findById(user_id);
		
	}

	
	//入力されたgrIdとglPassがあっているか調べる
	public Optional<GroupMstEntity> getGrInfo(String group_Id, String group_pass){
		return gInfoRepojitory.findByGroupIdAndGroupPass(group_Id, group_pass);
			
	}
	

	
	//DBのなかみを全部取ってくる
	public List<ThermoInfoEntity> getUserDate(){
		return uInfoRepository.findAll();
	}
	
	//DBのなかみを全部取ってくる
	//public List<Group_mstEntity> getGroup(){
	//	return testR.findAll();
	//}
	
	public void create(ThermoInfoEntity uInfoData) {		
		uInfoRepository.saveAndFlush(uInfoData);//Entityに登録
	}

	
}
