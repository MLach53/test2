package com.spr.systemplacereservation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.OfficeBuilding;

@Repository
public interface OfficeBuildingRepository extends MongoRepository<OfficeBuilding, Integer> {

}
