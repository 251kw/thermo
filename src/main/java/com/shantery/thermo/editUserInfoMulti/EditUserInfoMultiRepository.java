package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Query(value="SELECT *"
			+" FROM user_info"
			+" WHERE user_id=?1"
            ,nativeQuery = true)
	ArrayList<UserInfoEntity> UserDelList(String user_id);
	
//	@Modifying
//	@Transactional
//	@Query(value="DELETE "
//			+" FROM thermo_info"
//			+" WHERE update_time < now() - INTERVAL 1 YEAR"
//            ,nativeQuery = true)
//	public void thermoDel();
	
//	public ArrayList<UserInfoEntity> UserDelete(String user_id);
}