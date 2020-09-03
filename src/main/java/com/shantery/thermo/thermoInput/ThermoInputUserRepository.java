package com.shantery.thermo.thermoInput;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * ユーザー情報をとってくるクラス
 * 
 */
@Repository
public interface ThermoInputUserRepository extends JpaRepository<UserInfoEntity, String> {
	
	//同じグループIDのユーザー情報をとってくる
	List<UserInfoEntity> findByGroupIdOrderByUpdateTime(String group_id);
	
}
