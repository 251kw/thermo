package com.shantery.thermo.groupUpdate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.GroupMstEntity;

@Service
public class EditGroupInfoService {
	@Autowired
	EditGroupInfoRepository gUpdateRepository; //呼び出すクラス
	@Autowired
	HttpSession session;
	
	/**
	 * グループ情報を更新するメソッド
	 * @param gUpdateData
	 */
	public void update(GroupMstEntity gUpdateData) {	
		GroupMstEntity gmt = new GroupMstEntity();
		//現在日時の取得と日付の書式設定
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //更新時間
		//エンティティにセット
		gmt.setGroup_id((String) session.getAttribute("loginGroupId")); //IDをセット
		gmt.setGroup_name(gUpdateData.getGroup_name()); //Nameをセット
		gmt.setGroup_pass(gUpdateData.getGroup_pass()); //Passをセット
		gmt.setUpdate_time(time.format(calendar.getTime())); //Timeをセット
		gUpdateRepository.saveAndFlush(gmt);
	}
}
