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
	
	//同じグループID、名前のユーザー情報をとってくる
	List<UserInfoEntity> findByGroupIdAndUserNameLikeOrderByUpdateTime(String group_id, String user_name);
	
	//同じグループID、学年のユーザー情報をとってくる
	List<UserInfoEntity> findByGroupIdAndGradeOrderByUpdateTime(String group_id, String grade);
	
	//同じグループID、名前、学年のユーザー情報をとってくる
	List<UserInfoEntity> findByGroupIdAndUserNameLikeAndGradeOrderByUpdateTime(String group_id, String user_name, String grade);
}
