package com.shantery.thermo.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;
import com.shantery.thermo.thermoInput.ThermoInputUserRepository;

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
	private ThermoInputUserRepository thmInUserRepository;
	
	@Autowired
	private SearchRepositoryCustom schRepCustom; //リポジトリカスタムを呼び出す
	
	/**
	 * 入力の状態を見分けて
	 * 検索するメソッド
	 * @param grouoId グループId
	 * @param form 検索入力情報
	 * @return list 検索結果
	 */
	public List<UserInfoEntity> userSearch(String groupId, SearchInfoForm form){
		
		List<UserInfoEntity> ulist = null;
		
		if(EMPTY.equals(form.getSch_date()) &&
				EMPTY.equals(form.getSch_name()) && 
					EMPTY.equals(form.getSch_grade())) {
			
			if(form.getSch_high()==null) {
				//未記入、未選択で全員分
				ulist = thmInUserRepository.findByGroupIdOrderByUpdateTime(groupId);
				
			} else {
				//☑高い人二週間分のデータを検索
//				return schRepository.searchHighThermo(groupId);
			}
		}
		
		if(!EMPTY.equals(form.getSch_name()) || !EMPTY.equals(form.getSch_grade())) {
			//名前か学年で検索をしていたら
			ulist = schRepCustom.searchQuery(groupId, form);
			
			if(ulist.size()==0) {
				//検索結果が無かったら
				return ulist;
			}
			
		} else {
			//名前と学年の指定が無かったら、全員分
			ulist = thmInUserRepository.findByGroupIdOrderByUpdateTime(groupId);
		}
		
		return ulist;
	}
	
	/**
	 * 今日の検温情報があるか検索するメソッド
	 * @param groupId グループId
	 * @return result	真偽値
	 */
//	public boolean isZeroCurDate(String groupId) {
//		boolean result = false;
//		List<ThermoInfoEntity> list = schRepository.searchCurDate(groupId);
//		
//		if (list.size()==0) {	//なければtrueを返す
//			result = true;
//		}
//	
//		return result;
//	}
	
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
	
	public List<UserInfoEntity> userlistCheck (List<UserInfoEntity> ulist,SearchInfoForm form){
		if(EMPTY.equals(form.getSch_date())) {
			int count = ulist.size();
			for(int i=0; i+1<count; i++) {
				for(int t=0; t<13; t++) {
					ulist.add(i*14, ulist.get(i));
				}
			}
		}
		
		return ulist;
	}
	
}