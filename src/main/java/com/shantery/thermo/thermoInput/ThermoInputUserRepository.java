package com.shantery.thermo.thermoInput;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.shantery.thermo.entity.UserInfoEntity;

/**
 * 
 */
@Repository
public interface ThermoInputUserRepository extends JpaRepository<UserInfoEntity, String> {
	List<UserInfoEntity> findByGroupIdIs(String group_id);
	
}