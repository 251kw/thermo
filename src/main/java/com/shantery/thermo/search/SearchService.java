package com.shantery.thermo.search;

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
				//未記入、未選択で二週間分のデータを検索
				return schRepository.searchUnconditional(groupId);
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
	 * ログインユーザーが一般か管理者、またはスーパーユーザか判断するメソッド
	 * @param loginuser ログインユーザ情報
	 * @return	result 真偽値
	 */
	public int isAdminFlg(UserInfoEntity loginuser) { 
		int result = 0;
		
		if(loginuser.getAdmin_flg().equals(KBN_VALUE_ADMIN_ON)) {	//管理者フラグであればtrue
			result = 1;
		} else if(loginuser.getAdmin_flg().equals("2")) {
			result = 2;
		}
		
		return result;
	}
	
}