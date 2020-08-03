package com.shantery.thermo.search;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchRepository extends JpaRepository<UserInfo, Long> {
	
//	@Query("SELECT t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age, t.thermo, t.taste_disorder, t.olfactory_disorder, t.cough, t.other\r\n" + 
//			"FROM sns.user_info u, sns.thermo_info t\r\n" + 
//			"WHERE u.user_id = t.user_id\r\n" + 
//			"AND t.regist_date = curdate()")
//	public  List<ThermoInfo> searchCurDate();	//今日のデータ取得
	
//	public List<UserInfo> findByGroupId(String group_id);
	
	@Query(value="SELECT * FROM sns.user_info WHERE group_id='1'", nativeQuery = true)
	public List<UserInfo> find();
	
	public List<UserInfo> findByGender(String gender);
	
	
	
}

