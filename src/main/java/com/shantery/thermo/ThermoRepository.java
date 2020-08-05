package com.shantery.thermo;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author k.takahashi
 * thermoパッケージ用のリポジトリー
 */
@Repository
public interface ThermoRepository extends JpaRepository<UserInfoEntity, String> {

	/**
	 *ユーザーIDを元に該当するユーザー情報を取得するためのメソッド
	 */
	public Optional<UserInfoEntity> findById(String user_id);
}

