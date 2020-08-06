package com.shantery.thermo.search;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shantery.thermo.entity.ThermoInfoEntity;

@Service
public class SearchService {

	@Autowired
	private SearchRepository schRepository;	
	 
//	@PersistenceContext
//	EntityManager entityManager;
	
//	@SuppressWarnings("unchecked")

	/*
	 * 指定された引数に従ってsql文作成
	 * リポジトリをよびだして、ｓｑｌ実行
	 * コントローラに結果のlistを返す
	 */
	public List<ThermoInfoEntity> querySearch(String group_id, String date, String name, String grade) {
		
		String qstr="SELECT *" 
				+" FROM user_info u, thermo_info t"
				+" WHERE u.user_id = t.user_id" 
				+" AND u.group_id = "+group_id;
		
		String and=" AND ";
		
		if(date!=null) {	
			qstr += and+"t.regist_date = '" +date+ "'";

			if(name!=null && name!="") {
				qstr += and+"u.user_name LIKE '%" +name+ "%'";
			}
			if(grade!=null) {
				qstr += and+"u.grade = " +grade+ "%'";
			}

		} else {

			if(name!=null) {
				qstr += and+"u.nser_name LIKE '%" +name+ "%'";

				if(grade!=null) {
					qstr += and+"u.grade = " +grade;
				}
				
			} else {
				if(grade!=null){
					qstr += and+"u.grade = " +grade;
				}
			}
		}
		
//		List<ThermoInfoEntity> list = (List<ThermoInfoEntity>)entityManager.createQuery(qstr).getResultList();
		
		return schRepository.searchQuery(qstr);
	}
}