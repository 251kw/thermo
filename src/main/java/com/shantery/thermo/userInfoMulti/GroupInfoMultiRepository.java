package com.shantery.thermo.userInfoMulti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.GroupMstEntity;

@Repository
public interface GroupInfoMultiRepository extends JpaRepository<GroupMstEntity, Long> {

}
