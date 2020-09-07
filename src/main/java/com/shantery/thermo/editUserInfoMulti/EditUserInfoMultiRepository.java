package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

@Repository
public interface EditUserInfoMultiRepository extends JpaRepository<UserInfoEntity, String> {

	public ArrayList<UserInfoEntity> findByGroupId(String group_id);
	public Optional<UserInfoEntity> findById(String user_id);
}