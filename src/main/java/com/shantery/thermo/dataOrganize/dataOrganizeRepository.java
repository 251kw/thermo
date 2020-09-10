package com.shantery.thermo.dataOrganize;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shantery.thermo.entity.ThermoInfoEntity;

public interface dataOrganizeRepository extends JpaRepository<ThermoInfoEntity, String>{

	/**
	 *一年経過した検温情報を削除する
	 */
	@Modifying
	@Transactional
	@Query(value="DELETE "
			+" FROM thermo_info"
			+" WHERE update_time < now() - INTERVAL 1 YEAR"
            ,nativeQuery = true)
	public void thermoDel();
	
	/**
	 * 一年経過した検温情報を持つユーザーのユーザーID取得
	 * @return
	 */
	@Query(value="SELECT DISTINCT user_id"
			+" FROM thermo_info"
			+" WHERE update_time < now() - INTERVAL 1 YEAR"
            ,nativeQuery = true)
	List<String> thermoDelList();
	
}
