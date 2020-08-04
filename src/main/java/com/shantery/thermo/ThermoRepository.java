package com.shantery.thermo;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.UserInfoEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ThermoRepository extends JpaRepository<UserInfoEntity, String> {

	public Optional<UserInfoEntity> findById(String user_id);
}

