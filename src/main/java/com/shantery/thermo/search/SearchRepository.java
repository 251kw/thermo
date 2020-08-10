package com.shantery.thermo.search;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.hibernate.query.QueryParameter;
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
			+" AND t.regist_date = curdate()", nativeQuery = true)
	List<ThermoInfoEntity> searchCurDate(@Param("group_id") String group_id);	
	
	
}
