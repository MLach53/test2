package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.entity.dto.ReservationWithoutDateDTO;
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
import com.spr.systemplacereservation.exceptions.BusinessLogicException;
import com.spr.systemplacereservation.repository.OfficeBuildingRepository;
import com.spr.systemplacereservation.repository.SeatRepository;

class ReservationServiceTest extends SystemplacereservationApplicationTests {

	private OfficeBuilding building;
	private OfficeBuilding buildingTwo;

	private Seat seatOne;
	private Seat seatTwo;
	private Seat seatThree;
	private Seat seatFour;
	private Seat seatFive;
	private Seat seatSix;

	private ReservationDTO dtoOne;
	private ReservationDTO dtoTwo;
	private ReservationDTO dtoThree;

	@Autowired
	private OfficeBuildingRepository repository;
	@Autowired
	private SeatRepository seatRepository;

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

		seatFive = new Seat();
		seatFive.setFloorNumber(1);
		seatFive.setOfficeBuilding(buildingTwo);
		seatFive.setSeatNumber("C");
		seatFive.setReservationeligible(true);
		seatRepository.save(seatFive);

		seatSix = new Seat();
		seatSix.setFloorNumber(1);
		seatSix.setOfficeBuilding(building);
		seatSix.setSeatNumber("C");
		seatSix.setReservationeligible(true);
		seatRepository.save(seatSix);

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

	@Test
	void testMakeReservation() {

		// when
		Reservation reservation = service.makeReservation(dtoOne);

		// then
		Assertions.assertNotNull(reservation);
		Assertions.assertNotNull(reservation.getSeat());
		Assertions.assertEquals(1, reservation.getSeat().getReservation().size());

	}

	@Test
	void testMakeReservationChairNotAvaliable() {

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.makeReservation(dtoTwo);

		});

		Assertions.assertEquals(0, seatFour.getReservation().size());

	}

	@Test
	void testMakeReservationUserAlreadyRegistered() {

		// given
		service.makeReservation(dtoOne);
		dtoOne.setSeatNumber(seatTwo.getSeatNumber());

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.makeReservation(dtoOne);

		});

		Assertions.assertEquals(1, seatOne.getReservation().size());

	}

	@Test
	void testMakeReservationSeatNotFound() {

		// given
		service.makeReservation(dtoOne);
		dtoOne.setSeatNumber(seatTwo.getSeatNumber());

		ReservationDTO dto = new ReservationDTO();
		dto.setFloorNumber(0);
		dto.setOfficeBuildingId(0);
		dto.setPersonId(0);
		dto.setSeatNumber("KK");
		dto.setDate(LocalDate.of(2022, 8, 14));

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.makeReservation(dto);

		});

	}

	@Test
	void testMakeReservationDataIntegrityViolationException() {

		// given
		service.makeReservation(dtoOne);
		dtoOne.setPersonId(3);

		int oldSize = seatOne.getReservation().size();

		// then
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {

			// when
			service.makeReservation(dtoOne);

		});

		Assertions.assertEquals(oldSize, seatOne.getReservation().size());

	}

	@Test
	void testDeleteReservation() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);

		// then
		Assertions.assertDoesNotThrow(() -> {

			// when
			service.deleteReservation(reservation.getId());

		});

		assertEquals(0, seatOne.getReservation().size());

	}

	@Test
	void testDeleteReservationEmptyResultDateAccess() {

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.deleteReservation(-1);

		});

	}

	@Test
	void testGetReservationsAtGivenDateAndSpan() {

		// given
		service.makeReservation(dtoOne);
		service.makeReservation(dtoThree);

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
	void testUpdateReservation() {

		// given
		Reservation reservation = service.makeReservation(dtoThree);
		Seat oldSeat = reservation.getSeat();
		int oldSeatId = oldSeat.getId().intValue();

		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 13));
		dto.setFloorNumber(seatOne.getFloorNumber());
		dto.setSeatNumber(seatOne.getSeatNumber());
		dto.setPersonId(reservation.getPersonId());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatOne.getOfficeBuilding().getId());

		// when
		Reservation updatedReservation = service.updateReservation(dto);
		int currentSeatId = updatedReservation.getSeat().getId().intValue();

		// then
		assertNotEquals(oldSeatId, currentSeatId);

		assertEquals(0, oldSeat.getReservation().size());
		assertEquals(1, seatOne.getReservation().size());
		assertEquals(updatedReservation, seatOne.getReservation().get(0));

	}

	@Test
	void testUpdateReservationUserAlreadyRegistered() {

		// given
		dtoThree.setDate(dtoOne.getDate());
		service.makeReservation(dtoThree);
		Reservation reservation = service.makeReservation(dtoOne);

		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(dtoOne.getDate());

		dto.setFloorNumber(seatFive.getFloorNumber());
		dto.setSeatNumber(seatFive.getSeatNumber());
		dto.setPersonId(reservation.getPersonId());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatFive.getOfficeBuilding().getId());

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.updateReservation(dto);

		});

		assertNotNull(repository.findById(reservation.getId()));
		assertEquals(0, seatFive.getReservation().size());
		assertEquals(1, seatOne.getReservation().size());
		assertEquals(1, seatThree.getReservation().size());

		System.out.println();
	}

	@Test
	void testUpdateReservationSwitchSeatsSameDateSameBuilding() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);
		Seat oldSeat = reservation.getSeat();
		int oldSeatId = oldSeat.getId().intValue();

		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(dtoOne.getDate());
		dto.setFloorNumber(seatSix.getFloorNumber());
		dto.setSeatNumber(seatSix.getSeatNumber());
		dto.setPersonId(reservation.getPersonId());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatSix.getOfficeBuilding().getId());

		// when
		Reservation updatedReservation = service.updateReservation(dto);
		int currentSeatId = updatedReservation.getSeat().getId().intValue();

		// then
		assertNotEquals(oldSeatId, currentSeatId);

	}

	@Test
	void testUpdateReservationNonExistentReservationId() {

		// given
		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(dtoOne.getDate());
		dto.setFloorNumber(seatSix.getFloorNumber());
		dto.setSeatNumber(seatSix.getSeatNumber());
		dto.setPersonId(4);
		dto.setId(-1);
		dto.setOfficeBuildingId(seatSix.getOfficeBuilding().getId());

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.updateReservation(dto);

		});

	}

	@Test
	void testUpdateReservationNonExistentSeat() {

		// given
		Reservation reservation = service.makeReservation(dtoOne);
		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(dtoOne.getDate());
		dto.setFloorNumber(3);
		dto.setSeatNumber("AN");
		dto.setPersonId(4);
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(-1);

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.updateReservation(dto);

		});

	}

	@Test
	void testUpdateReservationChairNotAvaliable() {
		// given
		Reservation reservation = service.makeReservation(dtoOne);
		UpdateReservationDTO dto = new UpdateReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 14));
		dto.setFloorNumber(seatFour.getFloorNumber());
		dto.setId(reservation.getId());
		dto.setOfficeBuildingId(seatFour.getOfficeBuilding().getId());
		dto.setPersonId(reservation.getPersonId());
		dto.setSeatNumber(seatFour.getSeatNumber());

		// then
		Assertions.assertThrows(BusinessLogicException.class, () -> {

			// when
			service.updateReservation(dto);

		});

	}

}
