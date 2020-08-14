package com.shantery.thermo.thermoInput;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

/**エンティティからデータベースを操作するためのクラス
 * @author t.kurihara
 */
@Repository
public interface ThermoInputRepository extends JpaRepository<ThermoInfoEntity, String>{

	ThermoInfoEntity findByUserIdAndRegistDate(String user_id, String date);

}