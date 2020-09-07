package com.shantery.thermo.thermoInput;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;


/**
 * エンティティからデータベースを操作するためのクラス
 * @author t.kurihara
 */
@Repository
public interface ThermoInputRepository extends JpaRepository<ThermoInfoEntity, String>{

	//ユーザーIDと登録日で体温情報を取得する
	ThermoInfoEntity findByUserIdAndRegistDate(String user_id, String date);

}