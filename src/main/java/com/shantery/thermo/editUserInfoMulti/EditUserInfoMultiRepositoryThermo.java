package com.shantery.thermo.editUserInfoMulti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shantery.thermo.entity.ThermoInfoEntity;

public interface EditUserInfoMultiRepositoryThermo extends JpaRepository<ThermoInfoEntity, String> {
	//検温情報を削除するsql
	@Modifying
	@Transactional
	@Query(value="DELETE "
			+" FROM thermo_info"
			+" WHERE user_id=?1"
            ,nativeQuery = true)
	public void thermoDel(String user_id);
}
