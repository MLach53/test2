package com.spr.systemplacereservation.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	@Autowired
	private VCheckReservationRepository vcheckRepoRepository;
	@Autowired
	private SeatRepositoryDAO dao;

	@Test
	void test() throws ParseException {

		// System.out.println(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(1,
		// "A", 2));

		// System.out.println("----------------------------------------");

		// System.out.println(officeBuildingRepository.findAll());
		// System.out.println(seatRepository.findAll());
		// System.out.println(reservationRepository.findAll());
		// System.out.println(vcheckRepoRepository.findAll());
		// SeatQuery squery = new SeatQuery();
		// squery.setBuildingOffice(1);
		// squery.setFloorNumber(2);
		// squery.setSeatNumber("A");
		// System.out.println(dao.findSeatBy(squery));

		//

		var a = new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-10");

		System.out.println(a.getMonth());
		System.out.println(a);

		System.out.println(vcheckRepoRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(a, 2, 2));
		// System.out.println(vcheckRepoRepository.findByDate(a));

	}

}
