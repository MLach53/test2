package com.spr.systemplacereservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

	@Query(value = "select r,s from Reservation r left outer join Seat s on s.id = r.seat.id where s.officeBuilding.id = :officeBuildingId AND r.personId= :personId AND r.date=:date")
	Optional<Reservation> findFirstByDateAndPersonIdAndOfficeBuildingId(@Param(value = "date") LocalDate date,
			@Param(value = "personId") Integer personId, @Param(value = "officeBuildingId") Integer officeBuildingId);

	@Query(value = "select r,s from Reservation r left outer join Seat s on s.id = r.seat.id where r.date = :date")
	List<Reservation> findByDate(@Param(value = "date") LocalDate date);

	@Query(value = "select r,s from Reservation r left outer join Seat s on s.id = r.seat.id where r.date between :startingDate AND :endingDate")
	List<Reservation> findByDateBetween(@Param(value = "startingDate") LocalDate startingDate,
			@Param(value = "endingDate") LocalDate endingDate);

}
