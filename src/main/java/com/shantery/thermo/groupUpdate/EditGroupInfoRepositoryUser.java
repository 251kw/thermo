package com.shantery.thermo.groupUpdate;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author d.ito
 *ログイン中のユーザーの属するグループIDを取得するためログインユーザー情報を取得
 */
public interface EditGroupInfoRepositoryUser extends JpaRepository<UserInfoEntity, String> {
	//ログインユーザーIDからグループIDを取得
	public Optional<UserInfoEntity> findById(String user_id);
	//グループIDからユーザー」情報を取得
	public ArrayList<UserInfoEntity> findByGroupId(String group_id);
}
