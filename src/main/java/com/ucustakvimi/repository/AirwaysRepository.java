package com.ucustakvimi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ucustakvimi.model.Airway;

public interface AirwaysRepository extends JpaRepository<Airway, Long> {

	Airway findById(Integer airwayId);

	List<Airway> findAllByOrderByIdDesc();

	List<Airway> findAllByOrderByIdAsc();
	
    @Cacheable("findByCode")
	Airway findByCode(String code);

}