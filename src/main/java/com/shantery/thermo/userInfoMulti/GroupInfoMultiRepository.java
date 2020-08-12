package com.shantery.thermo.userInfoMulti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.GroupMstEntity;

/**
 * @author y.okino
 *sqlを実行し、データ登録,取得を行う
 */
@Repository
public interface GroupInfoMultiRepository extends JpaRepository<GroupMstEntity, Long> {

}
