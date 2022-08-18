package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.ChairNotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;
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

	private ReservationDTO dtoOne;
	private ReservationDTO dtoTwo;
	private ReservationDTO dtoThree;
	private ReservationDTO dtoFour;

	@Autowired
	private OfficeBuildingRepository repository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ReservationServiceImpl service;

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
		seatOne.setReservationeligible(true);
		seatRepository.save(seatOne);

		seatTwo = new Seat();
		seatTwo.setFloorNumber(0);
		seatTwo.setOfficeBuilding(building);
		seatTwo.setSeatNumber("B");
		seatTwo.setReservationeligible(true);
		seatRepository.save(seatTwo);

		seatThree = new Seat();
		seatThree.setFloorNumber(0);
		seatThree.setOfficeBuilding(buildingTwo);
		seatThree.setSeatNumber("A");
		seatThree.setReservationeligible(true);
		seatRepository.save(seatThree);

		seatFour = new Seat();
		seatFour.setFloorNumber(0);
		seatFour.setOfficeBuilding(buildingTwo);
		seatFour.setSeatNumber("B");
		seatFour.setReservationeligible(false);
		seatFour.setReDescription("seat is under maintenance");
		seatRepository.save(seatFour);

		dtoOne = new ReservationDTO();
		dtoOne.setDate(LocalDate.of(2022, 8, 14));
		dtoOne.setFloorNumber(seatOne.getFloorNumber());
		dtoOne.setSeatNumber(seatOne.getSeatNumber());
		dtoOne.setOfficeBuildingId(seatOne.getOfficeBuilding().getId());
		dtoOne.setPersonId(5);

		dtoTwo = new ReservationDTO();
		dtoTwo.setDate(LocalDate.of(2022, 8, 15));
		dtoTwo.setFloorNumber(seatFour.getFloorNumber());
		dtoTwo.setSeatNumber(seatFour.getSeatNumber());
		dtoTwo.setOfficeBuildingId(seatFour.getOfficeBuilding().getId());
		dtoTwo.setPersonId(5);

		dtoThree = new ReservationDTO();
		dtoThree.setDate(LocalDate.of(2022, 8, 16));
		dtoThree.setFloorNumber(seatThree.getFloorNumber());
		dtoThree.setSeatNumber(seatThree.getSeatNumber());
		dtoThree.setOfficeBuildingId(seatThree.getOfficeBuilding().getId());
		dtoThree.setPersonId(5);

	}

	@Disabled
	@Test
	void testMakeReservation() {

		// when
		Reservation reservation = service.makeReservation(dtoOne);

		// then
		Assertions.assertNotNull(reservation);
	}

	@Disabled
	@Test
	void testMakeReservationChairNotAvaliable() {

		// then
		Assertions.assertThrows(ChairNotAvailableException.class, () -> {

			// when
			service.makeReservation(dtoTwo);

		});

	}

	@Disabled
	@Test
	void testMakeReservationUserAlreadyRegistered() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);
		dtoOne.setSeatNumber(seatTwo.getSeatNumber());

		// then
		Assertions.assertThrows(UserAlreadyReservedChairException.class, () -> {

			// when
			service.makeReservation(dtoOne);

		});

	}

	@Disabled
	@Test
	void testMakeReservationNoSuchElementException() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);
		dtoOne.setSeatNumber(seatTwo.getSeatNumber());

		ReservationDTO dto = new ReservationDTO();
		dto.setFloorNumber(0);
		dto.setOfficeBuildingId(0);
		dto.setPersonId(0);
		dto.setSeatNumber("KK");
		dto.setDate(LocalDate.of(2022, 8, 14));

		// then
		Assertions.assertThrows(NoSuchElementException.class, () -> {

			// when
			service.makeReservation(dto);

		});

	}

	@Disabled
	@Test
	void testMakeReservationDataIntegrityViolationException() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);
		dtoOne.setPersonId(3);

		// then
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {

			// when
			service.makeReservation(dtoOne);

		});

	}

	@Disabled
	@Test
	void testDeleteReservation() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);

		// then
		Assertions.assertDoesNotThrow(() -> {

			// when
			service.deleteReservation(reservation.getId());

		});

	}

	@Disabled
	@Test
	void testDeleteReservationEmptyResultDateAccess() {

		// then
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

			// when
			service.deleteReservation(-1);

		});

	}

	@Disabled
	@Test
	void testGetReservationsAtGivenDateAndSpan() {

		// given
		service.makeReservation(dtoOne);
		service.makeReservation(dtoThree);

		// dtoThree.setOfficeBuildingId(seatThree.getOfficeBuilding().getId());
		// dtoThree.setSeatNumber(seatThree.getSeatNumber());
		// dtoThree.setFloorNumber(seatThree.getFloorNumber());
		dtoThree.setDate(LocalDate.of(2022, 8, 14));
		dtoThree.setPersonId(4);

		service.makeReservation(dtoThree);

		// when
		List<ReservationDTO> list1408 = service.getReservationsAtGivenDate(LocalDate.of(2022, 8, 14));
		List<ReservationDTO> list1508 = service.getReservationsAtGivenDate(LocalDate.of(2022, 8, 15));
		List<ReservationDTO> list1608 = service.getReservationsAtGivenDate(LocalDate.of(2022, 8, 16));

		Map<LocalDate, List<ReservationWithoutDateDTO>> betweenDates = service
				.getReserervationsAtGivenTimeSpan(LocalDate.of(2022, 8, 14), LocalDate.of(2022, 8, 16));

		// then
		assertEquals(2, list1408.size());
		assertEquals(1, list1608.size());
		assertEquals(0, list1508.size());
		assertEquals(2, betweenDates.size());

		assertEquals(list1408.size(), betweenDates.get(LocalDate.of(2022, 8, 14)).size());
		assertEquals(list1608.size(), betweenDates.get(LocalDate.of(2022, 8, 16)).size());

	}

	@Test
	@Disabled
	void testUpdateReservation() {

		Reservation reservation = service.makeReservation(dtoThree);

		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 13));
		dto.setFloorNumber(seatOne.getFloorNumber());
		dto.setSeatNumber(seatOne.getSeatNumber());
		dto.setPersonId(reservation.getPersonId());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatOne.getOfficeBuilding().getId());

		Reservation updatedReservation = service.updateReservation(dto);

		assertNotEquals(reservation, updatedReservation);

	}

	@Test
	void testUpdateReservation2() {

		Reservation reservation = service.makeReservation(dtoThree);

		System.out.println("before");
		System.out.println(reservation);

		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 13));
		dto.setFloorNumber(seatOne.getFloorNumber());
		dto.setSeatNumber(seatOne.getSeatNumber());
		dto.setPersonId(reservation.getPersonId());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatOne.getOfficeBuilding().getId());

		Reservation updatedReservation = service.updateReservation2(dto);

		System.out.println("after");
		System.out.println(updatedReservation);
		System.out.println(reservation);

		// System.out.println(reservation.equals(updatedReservation));

		assertNotEquals(reservation, updatedReservation);

	}

}
