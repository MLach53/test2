package com.spr.systemplacereservation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

	Optional<Reservation> findFirstByDateAndPersonIdAndOfficeBuildingId(LocalDate date, Integer personId,
			Integer officeBuildingId);

	List<Reservation> findAllbyDate(LocalDate date);

}
