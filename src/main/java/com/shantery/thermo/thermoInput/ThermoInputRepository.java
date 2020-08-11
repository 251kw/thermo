package com.shantery.thermo.thermoInput;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.shantery.thermo.entity.ThermoInfoEntity;

@Repository
public interface ThermoInputRepository extends JpaRepository<ThermoInfoEntity, String>{




}