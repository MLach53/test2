package com.spr.systemplacereservation.repository;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;

class RepositoriesFindAllTest extends SystemplacereservationApplicationTests {

	@Autowired
	private OfficeBuildingRepository officeBuildingRepository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	@Transactional
	void test() {

		System.out.println(officeBuildingRepository.findAll());
		System.out.println(seatRepository.findAll());
		System.out.println(reservationRepository.findAll());

	}

}
