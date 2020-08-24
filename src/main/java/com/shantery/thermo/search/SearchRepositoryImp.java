package com.shantery.thermo.search;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shantery.thermo.entity.ThermoInfoEntity;
import static com.shantery.thermo.util.ThermoConstants.*;
/**
 * @author y.sato
 * 検索動的クエリ実装クラス
 * 
 */
@Component
public class SearchRepositoryImp implements SearchRepositoryCustom {
	@Autowired
	HttpSession session; 
	
    @PersistenceContext
    EntityManager entityManager;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ThermoInfoEntity> searchQuery(String groupId, SearchInfoForm form) {
    	
    	StringBuilder sql = new StringBuilder();
    	
    	boolean dateFlg = false;
    	boolean nameFlg = false;
    	boolean gradeFlg = false;
    	
    	sql.append("SELECT t ");
    	sql.append("FROM ThermoInfoEntity t ");
    	sql.append("WHERE t.userInfoEntity.groupId = :groupId ");
    	
    	
    	if(!EMPTY.equals(form.getSch_date())) {			//日付が入力されたら
    		sql.append("AND t.registDate = :date ");
    		dateFlg = true;
    		
    		if(!EMPTY.equals(form.getSch_name())) {		//名前が入力されたら
    			sql.append("AND t.userInfoEntity.userName LIKE :name ");
    			nameFlg = true;
    		}
    		if(!EMPTY.equals(form.getSch_grade())) {		//学年が入力されたら
    			sql.append("AND t.userInfoEntity.grade = :grade ");
    			gradeFlg = true;
    		}
    	} else {
    		
    		if(!EMPTY.equals(form.getSch_name())) {
    			sql.append("AND t.userInfoEntity.userName LIKE :name ");
    			nameFlg = true;
    			
    			if(!EMPTY.equals(form.getSch_grade())) {
        			sql.append("AND t.userInfoEntity.grade = :grade ");
        			gradeFlg = true;
        		}
    		}
    		
    		if(!EMPTY.equals(form.getSch_grade())) {
    			sql.append("AND t.userInfoEntity.grade = :grade ");
    			gradeFlg = true;
    		}
    	}
    	
    	if(!dateFlg) { //日付指定がなかったら、二週間分を設定
			sql.append("AND t.registDate BETWEEN :endDate AND :curDate ");
    		sql.append("order by t.userInfoEntity.userName, t.registDate desc ");	//名前でまとめて、日付順に
	    }
    	
    	Query query = entityManager.createQuery(sql.toString());
    	
    	query.setParameter("groupId", groupId);
    	
    	//以下、フラグがtrueの時に値をセット
		if (dateFlg) {
			query.setParameter("date", form.getSch_date());
		} else {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
			
			String curDate = sdf.format(cal.getTime());		//今日の日付
			
			cal.add(Calendar.DAY_OF_MONTH, -14);			//日付計算
			String endDate = sdf.format(cal.getTime());		//二週間前の日付
			
			query.setParameter("curDate", curDate);
			query.setParameter("endDate", endDate);
		}
		if (nameFlg) query.setParameter("name", Q_PERCENT+form.getSch_name()+Q_PERCENT);
		if (gradeFlg) query.setParameter("grade", form.getSch_grade());
		
		return query.setMaxResults(MAX_SCH_LISTINT).getResultList();		//取得データ数の制限.結果をlistで取得
    }

}