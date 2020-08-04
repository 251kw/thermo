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
	
//	@Query("SELECT DISTINCT t FROM thermo_info t INNER JOIN t.user_info WHERE t.regist_date = '2020-08-03'")
//	List<ThermoInfo> searchCurDate();	//今日のデータ取得
	
	@Query(value="SELECT t.regist_date, u.user_name, u.gender, u.grade, TIMESTAMPDIFF(YEAR, u.birthday, curdate()) AS age"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date = curdate()", nativeQuery = true)
	List<SearchEntity> searchCurDate(@Param("group_id") String group_id);	//今日のデータ取得
	
//	public List<UserInfo> findByGroupId(String group_id);
	
//	public List<ThermoInfo> find();
	
//	public List<UserInfo> findByGender(String gender);
	
	
	
}

