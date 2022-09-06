package com.spr.systemplacereservation.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

	
	/**
	 * 
	 * @param date use special function for param date
	 * @return
	 */
	@Aggregation(pipeline = {   "  {'$match':{'personId': ?1, 'date': ?0 } } ",
								"  {'$lookup':{ 'from':'SEAT', 'localField': 'seat_id', 'foreignField':'_id', 'as':'seat'}}",
								"  {'$unwind': '$seat'}",
								"  {'$match': {'seat.officebuilding_id': ?2 }}"
								
	
	})
	Optional<Reservation> findFirstByDateAndPersonIdAndOfficeBuildingId(Date date,
			Integer personId, ObjectId officeBuildingId);
	
	
	
	
	/**
	 * 
	 * @param date use special function for param date
	 * @return
	 */
	
	@Aggregation(pipeline = {   "  {'$match':{'date': ?0 } } ",
			"  {'$lookup':{ 'from':'SEAT', 'localField': 'seat_id', 'foreignField':'_id', 'as':'seat'}}",
			"  {'$unwind':'$seat'}",
			"  {'$lookup': { 'from':'OFFICEBUILDING', 'as': 'officeBuilding', 'localField':'seat.officebuilding_id', 'foreignField':'_id'}}",
			"  {'$unwind': '$officeBuilding'}"
	})
	List<Reservation> findByDate(Date date);

	
	
	@Query("{'date': {'$gte': ?0, '$lte': ?1}}")
	
	
	/**
	 * 
	 * @param date use special function for param date
	 * @return
	 */
	@Aggregation(pipeline = {   "  {'$match':{'date': {'$gte': ?0, '$lte': ?1} } } ",
			"  {'$lookup':{ 'from':'SEAT', 'localField': 'seat_id', 'foreignField':'_id', 'as':'seat'}}",
			"  {'$unwind':'$seat'}"
	})
	List<Reservation> findByDateBetween(Date startingDate,
			Date endingDate);

}
