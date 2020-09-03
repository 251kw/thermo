package com.shantery.thermo.userInfo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author h.komatsu
 * sqlを実行し、データ登録を行う
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
	
	//新規登録のグループIDとグループパスを受け取り、DBで探す
	public Optional<UserInfoEntity> findById(String user_id);
	
	
}
