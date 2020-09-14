package com.shantery.thermo.dataOrganize;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shantery.thermo.entity.UserInfoEntity;

public interface dataOrganizeRepositoryUser extends JpaRepository<UserInfoEntity, String> {
	/**
	 * ユーザーIDからユーザーを削除するSQL
	 * @param user_id
	 */
	@Transactional
	@Modifying
	@Query(value="DELETE user_info"
			+" FROM user_info LEFT JOIN thermo_info"
			+" ON user_info.user_id = thermo_info.user_id"
			+" WHERE user_info.user_id=?1"
			+" AND thermo_info.regist_date IS NULL"
			+" AND user_info.update_time < now() - INTERVAL 1 YEAR"
            ,nativeQuery = true)
	public void userDel(String user_id);
	
	/**
	 * スーパーユーザー以外の検温情報を保持していないかつ、最終更新日が一年以上経過しているユーザーの情報を削除
	 * @return
	 */
	@Transactional
	@Modifying
	@Query(value="DELETE user_info"
			+" FROM user_info LEFT JOIN thermo_info"
			+" ON user_info.user_id = thermo_info.user_id"
			+" WHERE admin_flg != 2"
			+" AND thermo_info.regist_date IS NULL"
			+" AND user_info.update_time < now() - INTERVAL 1 YEAR"
            ,nativeQuery = true)
	public void thermoNullList();
}
