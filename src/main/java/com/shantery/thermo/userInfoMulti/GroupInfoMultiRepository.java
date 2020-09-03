package com.shantery.thermo.userInfoMulti;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author h.komatsu
 * sqlを実行し、データ登録を行う
 */
@Repository
public interface GroupInfoMultiRepository extends JpaRepository<GroupMstEntity, String> {
	
	//新規登録のグループIDとグループパスを受け取り、DBで探す
	public Optional<GroupMstEntity> findById(String group_id);
	
	//新規登録時のグループIDとグループパスワードを受け取り、一致するか
	public Optional<GroupMstEntity> findByGroupIdAndGroupPass(String group_id, String group_pass);
	
}

