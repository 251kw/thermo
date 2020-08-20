package com.shantery.thermo.search;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

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
		
		if("".equals(form.getSch_date()) &&
				"".equals(form.getSch_name()) && 
					"".equals(form.getSch_grade())) {
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
		
		if(loginuser.getAdmin_flg().equals("1")) {	//管理者フラグであればtrue
			result = true;
		}
		
		return result;
	}

	public boolean strCheck(String sch_name){
		if (!sch_name.matches("[a-zA-Z0-9ａ-ｚA-Zぁ-んァ-ヶー一-龠 　]+$")) {
			return false;
		}
		return true;
	}
}