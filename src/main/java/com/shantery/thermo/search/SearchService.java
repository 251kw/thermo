package com.shantery.thermo.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

import static com.shantery.thermo.util.ThermoConstants.*;

/**
 * @author y.sato
 * コントローラーから呼び出されて
 * 検索条件によってsql実行を振り分ける
 * 
 */
@Service
public class SearchService {
	
	@Autowired
	private SearchRepository schRepository;	//リポジトリクラス呼び出す
	
	@Autowired
	private SearchRepositoryCustom schRepCus; //リポジトリカスタムを呼び出す
	
	/**
	 * 入力の状態を見分けて
	 * 検索するメソッド
	 * @param grouoId グループId
	 * @param form 検索入力情報
	 * @return list 検索結果
	 */
	public List<ThermoInfoEntity> separate(String groupId, SearchInfoForm form){
		
		if(EMPTY.equals(form.getSch_date()) &&
				EMPTY.equals(form.getSch_name()) && 
					EMPTY.equals(form.getSch_grade())) {
			if(form.getSch_high()==null) {
				//未記入、未選択で今日のデータ検索にするのか
				return schRepository.searchCurDate(groupId);
			} else {
				//高い人二週間分のデータを検索
				return schRepository.searchHighThermo(groupId);
			}
		}
		
		
		return schRepCus.searchQuery(groupId, form);	//条件検索;
	}
	
	/**
	 * 今日の検温情報があるか検索するメソッド
	 * @param groupId グループId
	 * @return result	真偽値
	 */
	public boolean isZeroCurDate(String groupId) {
		boolean result = false;
		List<ThermoInfoEntity> list = schRepository.searchCurDate(groupId);
		
		if (list.size()==0) {	//なければtrueを返す
			result = true;
		}
	
		return result;
	}
	
	/**
	 * ログインユーザーは管理者か判断するメソッド
	 * @param loginuser ログインユーザ情報
	 * @return	result 真偽値
	 */
	public boolean isAdminFlg(UserInfoEntity loginuser) { 
		boolean result = false;
		
		if(loginuser.getAdmin_flg().equals(KBN_VALUE_ADMIN_ON)) {	//管理者フラグであればtrue
			result = true;
		}
		
		return result;
	}

	/**
	 * 検索情報（名前）の入力チェック
	 * @param sch_name 名前検索情報
	 * @return 真偽値
	 */
	public boolean nameCheck(String sch_name){
		if (!sch_name.matches(SCH_INFO_REGEX_PATTERN)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 検索情報（日付）の入力チェック
	 * @param sch_date
	 * @return 真偽値
	 */
	public boolean dateCheck(String sch_date) {
		DateFormat df = new SimpleDateFormat(DATE_PATTERN);
		 
		df.setLenient(false);	//日付を厳密にチェック
		 
		try {
			// 日付妥当性
			df.parse(sch_date);
		 
		} catch (ParseException e) {
			return false;
		}
		
		return true;
	}
}