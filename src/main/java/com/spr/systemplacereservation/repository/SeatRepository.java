package com.spr.systemplacereservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Seat;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer> {

	@Query(value = "select s,o from Seat s left outer join OfficeBuilding o on s.officeBuilding.id = o.id where o.id = :officeBuildingId AND s.seatNumber= :seatNumber AND s.floorNumber=:floorNumber")
	Optional<Seat> findByOfficeBuildingIdAndSeatNumberAndFloorNumber(
			@Param("officeBuildingId") Integer officeBuildingId, @Param("seatNumber") String seatNumber,
			@Param("floorNumber") Integer floorNumber);
}
