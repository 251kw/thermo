package com.shantery.thermo.thermoInput;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.search.SearchInfoForm;

import static com.shantery.thermo.util.ThermoConstants.*;

@Service
public class ThermoInputService {

	@Autowired
	ThermoInputRepository repository;
	@Autowired
	HttpSession session;
	@Autowired
	ThermoInputUserRepository u_repository;
	
	
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
				thEn.setThermo_id(THERMO_ID+Integer.toString(thermoId));
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
		//空欄つぶす
		thermo = thermo.trim();
		thermo = Normalizer.normalize(thermo,Normalizer.Form.NFKC);
		//空文字をnullにする
		if(thermo == EMPTY) {
			thermo = null;
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
			String check = Normalizer.normalize(lt.getTemperature().trim(),Normalizer.Form.NFKC);
			if(check.matches("^\\d+\\.?\\d{0,1}$") || check.equals(EMPTY)) {
				if(check.matches("^[3-4][0-9](.[0-9])?$") || check.equals(EMPTY)) {
					message.add(null);
				}else {
					message.add(THERMO_INP_TEMP_ER);
				}
			}else {
				message.add(THERMO_INP_ER);
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
			}
			i++;
		}
		
		return list;
	}
	
	public Iterable<UserInfoEntity> searchUserList(Iterable<UserInfoEntity> ulist, SearchInfoForm form){
		//ログインユーザー情報取得
		UserInfoEntity loginuser = (UserInfoEntity)session.getAttribute(LOGIN_USER);
		
		if(!EMPTY.equals(form.getSch_name())) {
			String rename = form.getSch_name().replaceAll("　", " ").replaceAll(" ", "");
			ulist = u_repository.findByGroupIdAndUserNameLikeOrderByUpdateTime(loginuser.getGroup_id(), Q_PERCENT+rename+Q_PERCENT);
			if(!EMPTY.equals(form.getSch_grade())) {
				ulist = u_repository.findByGroupIdAndUserNameLikeAndGradeOrderByUpdateTime(loginuser.getGroup_id(), Q_PERCENT+rename+Q_PERCENT, form.getSch_grade());
			}
		}else if(!EMPTY.equals(form.getSch_grade())) {
			ulist = u_repository.findByGroupIdAndGradeOrderByUpdateTime(loginuser.getGroup_id(), form.getSch_grade());
		}
		
		if(EMPTY.equals(form.getSch_name())&&EMPTY.equals(form.getSch_grade())) {
			ulist = u_repository.findByGroupIdOrderByUpdateTime(loginuser.getGroup_id());
		}
		
		return ulist;
	}
}
