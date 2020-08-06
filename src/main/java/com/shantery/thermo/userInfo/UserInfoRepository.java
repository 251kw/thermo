package com.shantery.thermo.userInfo;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * @author h.komatsu
 * sqlを実行し、データ登録を行う
 */
@Repository
public interface UserInfoRepository extends JpaRepository<ThermoInfoEntity, String> {
	
	//新規登録のグループIDとグループパスを受け取り、DBで探す
	public Optional<ThermoInfoEntity> findById(String user_id);
	
	//新規登録時のグループIDとグループパスワードを受け取り、検索する
	//public List<UserInfoEntity> searchGroup(@Param("grId") String grId, @Param("grPass") String grPass);
	
}
