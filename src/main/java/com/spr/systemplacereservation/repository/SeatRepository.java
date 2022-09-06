package com.spr.systemplacereservation.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Seat;

@Repository
public interface SeatRepository extends MongoRepository<Seat, String> {

	//@Query("{ 'floor_number' : ?2, 'seat_number' : ?1, 'officebuilding_id.$id': ?0    }")
	
	
	
	@Aggregation(pipeline = {
			"{'$match': {'floor_number': ?2, 'seat_number': ?1} }",
			"{'$lookup': { 'from':'OFFICEBUILDING', 'as':'officeBuilding', 'localField':'officebuilding_id','foreignField':'_id'  } }",
			"{'$unwind':'$officeBuilding'}"
			
	})
	Optional<Seat> findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
			 ObjectId officeBuildingId,  String seatNumber,
			 Integer floorNumber);
}
