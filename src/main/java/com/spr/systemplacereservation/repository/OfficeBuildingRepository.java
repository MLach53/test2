package com.spr.systemplacereservation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.OfficeBuilding;

@Repository
public interface OfficeBuildingRepository extends MongoRepository<OfficeBuilding, String> {

	
	@Query("{'name' : ?0}")
	OfficeBuilding findByNamePun(String name);
}
