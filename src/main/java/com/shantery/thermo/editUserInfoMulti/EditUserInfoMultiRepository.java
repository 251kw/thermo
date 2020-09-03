package com.shantery.thermo.editUserInfoMulti;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

@Repository
public interface EditUserInfoMultiRepository extends JpaRepository<UserInfoEntity, String> {

	public ArrayList<UserInfoEntity> findByGroupId(String group_id);
}