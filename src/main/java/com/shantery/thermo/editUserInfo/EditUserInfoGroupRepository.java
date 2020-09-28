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
public interface EditUserInfoGroupRepository extends JpaRepository<GroupMstEntity, String> {
	
	//新規登録のグループIDとグループパスを受け取り、DBで探す
	public Optional<GroupMstEntity> findById(String Group_id);
	
	
}
