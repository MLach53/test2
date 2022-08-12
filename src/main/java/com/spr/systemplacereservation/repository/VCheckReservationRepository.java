package com.spr.systemplacereservation.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.spr.systemplacereservation.entity.VCheckReservation;

@org.springframework.stereotype.Repository
public interface VCheckReservationRepository extends Repository<VCheckReservation, Integer> {

	/**
	 * 
	 * @param date             use: new
	 *                         SimpleDateFormat("yyyy-MM-dd").parse("your-date-here")
	 * @param personId
	 * @param officeBuildingId
	 * @return VCheckReservation if exists
	 */
	Optional<VCheckReservation> findFirstByDateAndPersonIdAndOfficeBuildingId(LocalDate date, Integer personId,
			Integer officeBuildingId);

}
