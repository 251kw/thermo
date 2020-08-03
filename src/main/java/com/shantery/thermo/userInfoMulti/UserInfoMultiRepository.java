package com.shantery.thermo.userInfoMulti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

@Repository
public interface UserInfoMultiRepository extends JpaRepository<UserInfoEntity, Long> {

	
	
}
