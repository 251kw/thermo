package com.shantery.thermo.editUserInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author h.komatsu
 * sqlを実行し、データ登録を行う
 */
@Repository
public interface EditGrInfoRepository extends JpaRepository<GroupMstEntity, String> {
	
	//新規登録時のグループIDとグループパスワードを受け取り、一致するか
	public Optional<GroupMstEntity> findByGroupIdAndGroupPass(String group_id, String group_pass);
	
	//ユーザー情報更新時、グループIDを受け取りDBで探す
	public GroupMstEntity findByGroupId(String group_id);
}
