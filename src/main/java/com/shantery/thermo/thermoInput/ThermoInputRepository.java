package com.shantery.thermo.thermoInput;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;

@Repository
public interface ThermoInputRepository extends JpaRepository<ThermoInfoEntity, String>{




}