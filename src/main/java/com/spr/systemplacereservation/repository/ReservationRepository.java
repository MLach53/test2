package com.spr.systemplacereservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, Integer> {

	// @Query(value = "select r,s from Reservation r left outer join Seat s on s.id
	// = r.seat.id where s.officeBuilding.id = :officeBuildingId AND r.personId=
	// :personId AND r.date=:date")
	Optional<Reservation> findFirstByDateAndPersonIdAndOfficeBuildingId(LocalDate date, Integer personId,
			String officeBuildingId);

	// @Query(value = "select r,s from Reservation r left outer join Seat s on s.id
	// = r.seat.id where r.date = :date")
	List<Reservation> findByDate(LocalDate date);

	// @Query(value = "select r,s from Reservation r left outer join Seat s on s.id
	// = r.seat.id where r.date between :startingDate AND :endingDate")
	List<Reservation> findByDateBetween(LocalDate startingDate, LocalDate endingDate);

}
