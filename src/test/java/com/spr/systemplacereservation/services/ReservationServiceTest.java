package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.repository.OfficeBuildingRepository;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;

class ReservationServiceTest extends SystemplacereservationApplicationTests {

	private Reservation one;
	private Reservation two;
	private Reservation three;

	private OfficeBuilding building;
	private OfficeBuilding buildingTwo;

	private Seat seatOne;
	private Seat seatTwo;
	private Seat seatThree;
	private Seat seatFour;

	@Autowired
	private OfficeBuildingRepository repository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private ReservationRepository reservationRepository;

	@BeforeEach
	void setUp() {

		building = new OfficeBuilding();
		building.setName("Rybnik");

		buildingTwo = new OfficeBuilding();
		buildingTwo.setName("Wodzisław śląski");

		repository.save(building);
		repository.save(buildingTwo);

		seatOne = new Seat();
		seatOne.setFloorNumber(0);
		seatOne.setOfficeBuilding(building);
		seatOne.setSeatNumber("A");
		seatRepository.save(seatOne);

		seatTwo = new Seat();
		seatTwo.setFloorNumber(0);
		seatTwo.setOfficeBuilding(building);
		seatTwo.setSeatNumber("B");
		seatRepository.save(seatTwo);

		seatThree = new Seat();
		seatThree.setFloorNumber(0);
		seatThree.setOfficeBuilding(buildingTwo);
		seatThree.setSeatNumber("A");
		seatRepository.save(seatThree);

		seatFour = new Seat();
		seatFour.setFloorNumber(0);
		seatFour.setOfficeBuilding(buildingTwo);
		seatFour.setSeatNumber("B");
		seatRepository.save(seatFour);

		one = new Reservation();
		two = new Reservation();
		three = new Reservation();

		one.setPersonId(1);
		one.setDate(LocalDate.of(2022, 8, 14));
		one.setSeat(seatOne);

		two.setPersonId(2);
		two.setDate(LocalDate.of(2022, 8, 14));
		two.setSeat(seatTwo);

		three.setDate(LocalDate.of(2022, 8, 15));
		three.setPersonId(1);
		three.setSeat(seatThree);

		reservationRepository.save(one);
		reservationRepository.save(two);
		reservationRepository.save(three);

	}

	// @Test
	void testFindFirstByDateAndPersonIdAndOfficeBuildingIdPresent() {

		// when
		Optional<Reservation> optional = reservationRepository
				.findFirstByDateAndPersonIdAndOfficeBuildingId(LocalDate.of(2022, 8, 14), 2, building.getId());

		// then
		assertEquals(true, optional.isPresent());

	}

	// @Test
	void testFindFirstByDateAndPersonIdAndOfficeBuildingIdEmpty() {

		// when
		Optional<Reservation> optional = reservationRepository
				.findFirstByDateAndPersonIdAndOfficeBuildingId(LocalDate.of(2022, 8, 15), 2, building.getId());

		// then
		assertEquals(true, optional.isEmpty());

	}

	// @Test
	void testFindByDate() {

		// when
		List<Reservation> value = reservationRepository.findByDate(LocalDate.of(2022, 8, 14));

		// then
		assertFalse(value.isEmpty());

	}

	// @Test
	void testFindByDateEmpty() {

		// when
		List<Reservation> value = reservationRepository.findByDate(LocalDate.of(2022, 8, 13));

		// then
		assertTrue(value.isEmpty());
	}

	@Test
	void testFindByDateBetween() {
		System.out.println("-------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------");
		System.out.println("ReservationRepositoryTest2.testFindByDateBetween()");

		reservationRepository.findByDateBetween(LocalDate.of(2022, 8, 13), LocalDate.of(2022, 8, 14))
				.forEach(System.out::println);

		System.out.println("-------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------");
	}

}
