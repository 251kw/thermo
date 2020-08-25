package com.shantery.thermo.search;

import java.util.List;

import static com.shantery.thermo.util.ThermoConstants.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;

/**
 * @author y.sato
 * 検索静的クエリ実行リポジトリ
 * 
 */
@Repository
public interface SearchRepository extends JpaRepository<ThermoInfoEntity, String>{
	
	//今日のデータ取得
	@Query(value="SELECT *"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date = curdate()"
			+" LIMIT "
			+MAX_SCH_LISTSTR, nativeQuery = true)
	List<ThermoInfoEntity> searchCurDate(@Param("group_id") String group_id);
	
	//過去2週間分のデータを取得
	@Query(value="SELECT *"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date >= DATE_ADD(now(), INTERVAL -14 DAY)"
			+" ORDER BY t.regist_date DESC, u.grade, u.user_name"
			+" LIMIT "
			+MAX_SCH_LISTSTR, nativeQuery = true)
	List<ThermoInfoEntity> searchUnconditional(@Param("group_id") String group_id);
	
	//過去2週間分の体温が高い人のデータを取得（☑単独、37度以上）
	@Query(value="SELECT *"
			+" FROM user_info u, thermo_info t"
			+" WHERE u.user_id = t.user_id"
			+" AND u.group_id = :group_id"
			+" AND t.regist_date >= DATE_ADD(now(), INTERVAL -14 DAY)"
			+" AND t.thermo >= 37"
			+" ORDER BY t.thermo DESC, t.regist_date DESC"
			+" LIMIT "
			+MAX_SCH_LISTSTR, nativeQuery = true)
	List<ThermoInfoEntity> searchHighThermo(@Param("group_id") String group_id);
	
	
}
