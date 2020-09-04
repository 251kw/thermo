package com.shantery.thermo.groupUpdate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author d.ito
 *ログインされているグループの情報を取得
 */
@Repository
public interface EditGroupInfoRepository extends JpaRepository<GroupMstEntity, String> {
	//グループIDからグループ情報を取得
	public List<GroupMstEntity> findByGroupId(String group_id);	
}
