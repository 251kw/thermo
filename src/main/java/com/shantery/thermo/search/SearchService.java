package com.shantery.thermo.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;

@Service
public class SearchService {

	@Autowired
	private SearchRepository schRepository;

	/*
	 * 指定された引数に従ってsql文作成
	 * リポジトリをよびだして、ｓｑｌ実行
	 * コントローラに結果のlistを返す
	 */
	public List<ThermoInfoEntity> createQuerySearch(String group_id, String date, String name, String grade) {
		
		String query="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other" 
				+" FROM user_info u, thermo_info t"
				+" WHERE u.user_id = t.user_id" 
				+" AND u.group_id = "+group_id;
		
		String and=" AND ";
		
		if(date!=null) {	
			query += and+"t.regist_date = '" +date+ "'";

			if(name!=null && name!="") {
				query += and+"u.user_name LIKE '%" +name+ "%'";
			}
			if(grade!=null) {
				query += and+"u.grade = " +grade+ "%'";
			}

		} else {

			if(name!=null) {
				query += and+"u.nser_name LIKE '%" +name+ "%'";

				if(grade!=null) {
					query += and+"u.grade = " +grade;
				}
				
			} else {
				if(grade!=null){
					query += and+"u.grade = " +grade;
				}
			}
		}
		
		
		return schRepository.searchQuery(query);
	}
}