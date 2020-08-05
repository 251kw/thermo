package com.shantery.thermo.thermoInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.ThermoKey;
import com.shantery.thermo.thermoInput.ThermoInputForm;

import antlr.collections.List;

@Service
public class ThermoInputService {

	
	@Autowired
	ThermoInputRepository repository;
	
	public void registAll(ArrayList<ThermoInputForm.Detail> list) {
		
		//現在日時の取得と日付の書式設定
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		int thermoId = (1+(int)repository.count());
		
		for(int i=0; i<list.size(); i++) {
			ThermoInfoEntity thEn = new ThermoInfoEntity();
			thEn.setUser_id(list.get(i).getUserId());
			thEn.setThermo_id(Integer.toString(thermoId));
			thermoId++;
			thEn.setThermo(list.get(i).getTemperature());
			thEn.setTaste_disorder(list.get(i).getTaste());
			thEn.setOlfactory_disorder(list.get(i).getSmell());
			thEn.setCough(list.get(i).getCough());
			thEn.setOther(list.get(i).getWriting());
			thEn.setRegist_date(day.format(calendar.getTime()));
			thEn.setUpdate_user("kurihara");//ログインID
			thEn.setUpdate_time(time.format(calendar.getTime()));
			
			repository.save(thEn);
			
		}
					
			
	}
	
	public String convertCheck(String check) {
		if(check.equals("on")) {
			check = "1";
		}else {
			check = "0";
		}
		
		return check;
	}
}

