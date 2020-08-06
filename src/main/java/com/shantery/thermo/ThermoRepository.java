package com.shantery.thermo;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author k.takahashi
 * thermoパッケージ用のリポジトリー
 */
@Repository
public interface ThermoRepository extends JpaRepository<ThermoInfoEntity, String> {

	/**
	 *ユーザーIDを元に該当するユーザー情報を取得するためのメソッド
	 */
	public Optional<ThermoInfoEntity> findById(String user_id);
}

