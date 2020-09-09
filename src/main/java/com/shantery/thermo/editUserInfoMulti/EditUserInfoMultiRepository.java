package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author k.takahashi
 * editUserInfoMultiパッケージで使うリポシトリー
 */
@Repository
public interface EditUserInfoMultiRepository extends JpaRepository<UserInfoEntity, String> {

	// 同じグループ内のユーザーを取得する用
	public ArrayList<UserInfoEntity> findByGroupIdOrderByUpdateTime(String group_id);
	
	// ログイン中のユーザー情報を取得する用
	public Optional<UserInfoEntity> findById(String user_id);
}