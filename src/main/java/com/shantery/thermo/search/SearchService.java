package com.shantery.thermo.search;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;

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
	
	@Autowired
	private HttpSession session;
	
	@SuppressWarnings("unchecked")
	public List<ThermoInfoEntity> separate(String groupId, SearchInfoForm form){
		
		if(!"".equals(form.getSch_date()) ||
				!"".equals(form.getSch_name()) ||
					!"".equals(form.getSch_grade())) {
			if(form.getSch_high()!=null) {
				//error
				return (List<ThermoInfoEntity>) session.getAttribute("schlist");
			}
		}
		
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

}