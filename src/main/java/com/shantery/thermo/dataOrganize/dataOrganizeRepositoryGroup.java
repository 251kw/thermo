package com.shantery.thermo.dataOrganize;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author d.ito
 *スーパーユーザーグループ以外のグループ情報を取得
 */
public interface dataOrganizeRepositoryGroup extends JpaRepository<GroupMstEntity, String> {
	
	/**
	 * @param group_id
	 * ユーザー情報を保持しないかつ、更新日が一年以上前のグループを削除
	 */
	@Transactional
	@Modifying
	@Query(value="DELETE group_mst"
			+" FROM group_mst"
			+" LEFT JOIN user_info"
			+" ON group_mst.group_id = user_info.group_id"
			+" WHERE group_mst.group_id NOT IN ('shantery')"	
			+" AND user_info.user_id IS NULL"
			+" AND group_mst.update_time < now() - INTERVAL 1 YEAR"
            ,nativeQuery = true)
	public void groupDel();
}
