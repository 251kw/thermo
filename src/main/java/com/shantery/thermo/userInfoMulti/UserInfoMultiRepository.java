package com.shantery.thermo.userInfoMulti;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

/**
 * sqlを実行し、データ登録を行う
 */
@Repository
interface UserInfoMultiRepository extends JpaRepository<UserInfoEntity, String> {
	
	//新規登録のグループIDとグループパスを受け取り、DBで探す
	Optional<UserInfoEntity> findById(String user_id);
	
	
}
