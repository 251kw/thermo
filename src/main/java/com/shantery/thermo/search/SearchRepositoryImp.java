package com.shantery.thermo.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shantery.thermo.entity.UserInfoEntity;

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
	//TODO 現状、全角英数字と半角英数字を区別してしまう。
	public List<UserInfoEntity> searchQuery(String groupId, SearchInfoForm form) {
		
		StringBuilder sql = new StringBuilder();
		
//		boolean dateFlg = false;
		boolean nameFlg = false;
		boolean gradeFlg = false;
		
		sql.append("SELECT u ");
		sql.append("FROM UserInfoEntity u ");
		sql.append("WHERE u.groupId = :groupId ");
		
		
//		if(!EMPTY.equals(form.getSch_date())) {			//日付が入力されたら
//			sql.append("AND t.registDate = :date ");
//			dateFlg = true;
//			
//			if(!EMPTY.equals(form.getSch_name())) {		//名前が入力されたら、文字列の空白は削除
//				sql.append("AND REPLACE(REPLACE(t.userInfoEntity.userName,' ', ''), '　', '') LIKE :name ");
//				nameFlg = true;
//			}
//			if(!EMPTY.equals(form.getSch_grade())) {		//学年が入力されたら
//				sql.append("AND t.userInfoEntity.grade = :grade ");
//				gradeFlg = true;
//			}
//		} else {
			
			if(!EMPTY.equals(form.getSch_name())) {
				sql.append("AND REPLACE(REPLACE(u.userName,' ', ''), '　', '') LIKE :name ");
				nameFlg = true;
				
				if(!EMPTY.equals(form.getSch_grade())) {
					sql.append("AND u.grade = :grade ");
					gradeFlg = true;
				}
			}
			
			if(!EMPTY.equals(form.getSch_grade())) {
				sql.append("AND u.grade = :grade ");
				gradeFlg = true;
			}
//		}
		
//		if(!dateFlg) { //日付指定がなかったら、二週間分を設定
//			sql.append("AND t.registDate BETWEEN :endDate AND :curDate ");
//			sql.append("order by t.registDate desc, t.userInfoEntity.grade, t.userInfoEntity.userName ");	//日付、学年、名前順で並べる
//		} else {
			sql.append("order by u.grade, u.userName ");
//		}
		
		Query query = entityManager.createQuery(sql.toString());
		
		query.setParameter("groupId", groupId);
		
		//以下、フラグがtrueの時に値をセット
//		if (dateFlg) {
//			query.setParameter("date", form.getSch_date());
//		} else {
//			Calendar cal = Calendar.getInstance();
//			SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
//			
//			String curDate = sdf.format(cal.getTime());		//今日の日付
//			
//			cal.add(Calendar.DAY_OF_MONTH, -13);			//日付計算
//			String endDate = sdf.format(cal.getTime());		//二週間前の日付
//			
//			query.setParameter("curDate", curDate);
//			query.setParameter("endDate", endDate);
//		}
		if (nameFlg) {	//入力された文字列の空白も削除
			String rename = form.getSch_name().replaceAll("　", " ").replaceAll(" ", "");
			query.setParameter("name", Q_PERCENT+rename+Q_PERCENT);
		}
		if (gradeFlg) query.setParameter("grade", form.getSch_grade());
		
		return query.setMaxResults(MAX_SCH_LISTINT).getResultList();		//取得データ数の制限.結果をlistで取得
    }

}