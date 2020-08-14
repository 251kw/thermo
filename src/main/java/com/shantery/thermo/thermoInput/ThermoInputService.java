package com.shantery.thermo.thermoInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import static com.shantery.thermo.util.ThermoConstants.*;

@Service
public class ThermoInputService {

	@Autowired
	ThermoInputRepository repository;
	@Autowired
	HttpSession session;
	
	
	/**エンティティにセットして登録する
	 * @param list Formに入った登録したい情報
	 */
	public void registAll(ArrayList<ThermoInputForm.Detail> list) {
		
		//現在日時の取得と日付の書式設定
		Calendar calendar = Calendar.getInstance();
		//登録時間
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//登録日時
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		//thermoIDを自動採番で決める
		int thermoId = (1+(int)repository.count());
		
		//updateuserとしてsessionからログインユーザーをとってくる
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		//一人ひとり登録
		for(int i=0; i<list.size(); i++) {
			ThermoInfoEntity thEn = new ThermoInfoEntity();
			thEn.setUser_id(list.get(i).getUserId());
			//登録されていた場合削除する
			ThermoInfoEntity user = repository.findByUserIdAndRegistDate(list.get(i).getUserId(), day.format(calendar.getTime()));
			if(user != null) {
				//ThermoIdを消すものと入れ替える
				thEn.setThermo_id(user.getThermo_id());
				repository.delete(user);
			}else {
				thEn.setThermo_id("t_"+Integer.toString(thermoId));
				thermoId++;
			}
			thEn.setThermo(convertNull(list.get(i).getTemperature()));
			thEn.setTaste_disorder(convertCheck(list.get(i).getTaste()));
			thEn.setOlfactory_disorder(convertCheck(list.get(i).getSmell()));
			thEn.setCough(convertCheck(list.get(i).getCough()));
			thEn.setOther(list.get(i).getWriting());
			thEn.setRegist_date(day.format(calendar.getTime()));
			thEn.setUpdate_user(loginuser.getUser_id());			
			thEn.setUpdate_time(time.format(calendar.getTime()));
			
			repository.saveAndFlush(thEn);
			
		}
					
			
	}
	
	/**
	 * チェックボックスの値を変換
	 * @param check チェックボックスの値
	 * @return 変換後の値
	 */
	public String convertCheck(String check) {
		if(check != null) {
			check = KBN_VALUE_WITH;
		}else {
			check = KBN_VALUE_WITHOUT;
		}
		
		return check;
	}
	
	/**
	 * 体温未入力時にnullを返す
	 * @param thermo 体温
	 * @return そのままの値かnull
	 */
	public String convertNull(String thermo) {
		if(thermo == EMPTY) {
			thermo = null;
		}
		
		return thermo;
	}
	
	public String convertCheckReturn(String check) {
		if(check.equals(KBN_VALUE_WITHOUT)) {
			check = null;
		}
		
		return check;
	}
}

