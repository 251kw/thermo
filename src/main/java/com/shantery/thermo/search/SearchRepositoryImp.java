package com.shantery.thermo.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.shantery.thermo.entity.ThermoInfoEntity;
/**
 * @author y.sato
 * 検索動的クエリ実装クラス
 * 
 */
@Component
public class SearchRepositoryImp implements SearchRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ThermoInfoEntity> searchQuery(String groupId, SearchInfoForm form) {
    	
    	StringBuilder sql = new StringBuilder();
    	
    	boolean dateFlg = false;
    	boolean nameFlg = false;
    	boolean gradeFlg = false;
    	
    	sql.append("SELECT t FROM ThermoInfoEntity t ");
    	sql.append("WHERE t.userInfoEntity.groupId = :groupId ");
    	
    	
    	if(!"".equals(form.getSch_date())) {			//日付が入力されたら
    		sql.append("AND t.registDate = :date ");
    		dateFlg = true;
    		
    		if(!"".equals(form.getSch_name())) {		//名前が入力されたら
    			sql.append("AND t.userInfoEntity.userName LIKE :name ");
    			nameFlg = true;
    		}
    		if(!"".equals(form.getSch_grade())) {		//学年が入力されたら
    			sql.append("AND t.userInfoEntity.grade = :grade ");
    			gradeFlg = true;
    		}
    	} else {
    		
    		if(!"".equals(form.getSch_name())) {
    			sql.append("AND t.userInfoEntity.userName LIKE :name ");
    			nameFlg = true;
    			
    			if(!"".equals(form.getSch_grade())) {
        			sql.append("AND t.userInfoEntity.grade = :grade ");
        			gradeFlg = true;
        		}
    		}
    		
    		if(!"".equals(form.getSch_grade())) {
    			sql.append("AND t.userInfoEntity.grade = :grade ");
    			gradeFlg = true;
    		}
    	}
    	
    	if(!dateFlg&&nameFlg) { //TODO 名前検索のときのデータ総数制御
    		sql.append("AND t.registDate BETWEEN CURRENT_DATE AND '2020-08-03' ");
    	}
    	
    	Query query = entityManager.createQuery(sql.toString());
    	
    	query.setParameter("groupId", groupId);
    	
    	//以下、フラグがtrueの時に値をセット
		if (dateFlg) query.setParameter("date", form.getSch_date());
		if (nameFlg) query.setParameter("name", "%"+form.getSch_name()+"%");
		if (gradeFlg) query.setParameter("grade", form.getSch_grade());
		
		return query.getResultList();
    }

}