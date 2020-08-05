package com.shantery.thermo.search;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, String> {
	
	//今日のデータ取得
	@Query(value="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date = curdate()", nativeQuery = true)
	List<SearchEntity> searchCurDate(@Param("group_id") String group_id);	
	
	//日付検索、検索条件が”日付”の場合、それを受け取って結果を返す
	@Query(value="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date = :regist_date", nativeQuery = true)
	List<SearchEntity> searchDate(@Param("regist_date") String regist_date, @Param("group_id") String group_id);	//検索条件（計測日）
	
	//ユーザ名検索、検索条件が”ユーザ名”の場合、それを受け取って結果を返す
	@Query(value="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND user_name = :user_name"
			+"LIMIT 14", nativeQuery = true)		//過去14日分
	List<SearchEntity> searchName(@Param("user_name") String user_name, @Param("group_id") String group_id);
	
	//学年検索、検索条件が”学年”の場合、それを受け取って結果を返す
	@Query(value="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND u.grade = :grade" 
			+" AND t.regist_date = curdate()", nativeQuery = true)
	List<SearchEntity> searchGrade(@Param("grade") String user_name, @Param("group_id") String group_id);
	
	//体温が高い人検索
	@Query(value="SELECT u.user_id, t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date >= DATE_ADD(now(), INTERVAL -14 DAY)"		//今日から過去14日分
			+" ORDER BY t.thermo DESC",  nativeQuery = true)
	List<SearchEntity> searchHighThermo(@Param("group_id") String group_id);
	
	@Query(value=":query", nativeQuery = true)
	List<SearchEntity> searchQuery(@Param("query") String query);
	
	
}

