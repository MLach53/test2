package com.spr.systemplacereservation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.OfficeBuilding;


@Repository
public interface OfficeBuildingRepository extends CrudRepository<OfficeBuilding, Integer> {

}
