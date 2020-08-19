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
	
	
	/**
	 * エンティティにセットして登録する
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
			thEn.setThermo(convertThermo(list.get(i).getTemperature()));
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
	 * 体温を変換
	 * @param thermo 体温
	 * @return そのままの値かnull
	 */
	public String convertThermo(String thermo) {
		//空文字をnullにする
		if(thermo == EMPTY) {
			thermo = null;
		}
		//全角を半角にする
		if(thermo != null) {
			StringBuffer sb = new StringBuffer(thermo);
			for (int i = 0; i < thermo.length(); i++) {
				char c = thermo.charAt(i);
				if ('０' <= c && c <= '９') {
					sb.setCharAt(i, (char) (c - '０' + '0'));
				}
				if (c == ',' || c == '．') {
					sb.setCharAt(i, (char)'.');
				}
			}
			thermo = sb.toString();
		}
		//小数第一位に０を付ける
		if(thermo != null) {
			if(thermo.matches("^[0-9]{2}$")) {
				thermo = thermo+".0";
			}
		}
		return thermo;
	}
	
	/**
	 * 空文字をnullに変換する
	 * @param check チェックしたい値
	 * @return チェック後の値
	 */
	public String convertCheckReturn(String check) {
		if(check.equals(KBN_VALUE_WITHOUT)) {
			check = null;
		}
		
		return check;
	}
	
	/**
	 * 体温の入力チェックをする
	 * @param list チェックしたい体温情報の入った入力情報
	 * @return エラーメッセージ
	 */
	public ArrayList<String> checkInput(ArrayList<ThermoInputForm.Detail> list){
		ArrayList<String> message = new ArrayList<String>();
		for(ThermoInputForm.Detail lt : list) {
			if(lt.getTemperature().matches("^[0-9０-９]{2}(.[0-9０-９]{1})?$") || lt.getTemperature().equals(EMPTY)) {
				message.add(null);
			}
			else {
				message.add("※半角数字で小数第一位まで入力してください");
			}
		}
		
		return message;
	}
	
	/**
	 * ユーザー情報を元に、そのユーザーの体温情報を取得する
	 * @param ulist ユーザー情報
	 * @return 表示用に変換した体温情報
	 */
	public ArrayList<ThermoInputForm.Detail> checkRegistDate(Iterable<UserInfoEntity> ulist){
		//現在日時の取得と日付の書式設定
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		//すでに登録している情報取得 
		ArrayList<ThermoInputForm.Detail> list = new ArrayList<ThermoInputForm.Detail>();
		int i = 0;
		for(UserInfoEntity ul : ulist) {
			ThermoInfoEntity user = repository.findByUserIdAndRegistDate(ul.getUser_id(), day.format(calendar.getTime()));
			list.add(new ThermoInputForm.Detail());
			//表示用に値を変換
			if(user != null) {
				list.get(i).setTemperature(user.getThermo());
				list.get(i).setTaste(convertCheckReturn(user.getTaste_disorder()));
				list.get(i).setSmell(convertCheckReturn(user.getOlfactory_disorder()));
				list.get(i).setCough(convertCheckReturn(user.getCough()));
				list.get(i).setWriting(user.getOther());
				i++;
			}
		}
		
		return list;
	}
}
