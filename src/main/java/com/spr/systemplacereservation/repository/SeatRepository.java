package com.spr.systemplacereservation.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Seat;

@Repository
public interface SeatRepository extends MongoRepository<Seat, Integer> {

	Optional<Seat> findByOfficeBuildingIdAndSeatNumberAndFloorNumber(String officeBuildingId, String seatNumber,
			Integer floorNumber);
}
