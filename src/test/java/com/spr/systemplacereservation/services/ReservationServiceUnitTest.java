package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;
import com.spr.systemplacereservation.translator.TranslatorService;

@ExtendWith(MockitoExtension.class)
class ReservationServiceUnitTest {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private SeatRepository seatRepository;

	@Mock
	private TranslatorService translator;

	private ReservationService reservationService;

	private ReservationDTO dto;
	private Reservation ret;

	@BeforeEach
	void setUp() throws Exception {

		reservationService = new ReservationServiceImpl(reservationRepository, seatRepository, translator);

		// given
		OfficeBuilding officeBuilding = new OfficeBuilding();
		officeBuilding.setId(1);
		officeBuilding.setName("Gliwice");

		Seat seat = new Seat();
		seat.setFloorNumber(1);
		seat.setId(1);
		seat.setOfficeBuilding(officeBuilding);
		seat.setReservationeligible(true);
		seat.setSeatNumber("A");

		officeBuilding.getSeats().add(seat);

		dto = new ReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 19));
		dto.setSeatNumber(seat.getSeatNumber());
		dto.setFloorNumber(seat.getFloorNumber());
		dto.setOfficeBuildingId(seat.getOfficeBuilding().getId());
		dto.setPersonId(2);
		dto.setCreatedOn(LocalDate.of(2022, 8, 17));

		when(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
				dto.getSeatNumber(), dto.getFloorNumber())).thenReturn(Optional.of(seat));

		Reservation reservation = new Reservation();
		reservation.setDate(dto.getDate());
		reservation.setPersonId(dto.getPersonId());
		reservation.setSeat(seat);
		reservation.setDate(dto.getDate());

		when(reservationRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
				dto.getOfficeBuildingId())).thenReturn(Optional.empty());

		ret = new Reservation();
		ret.setDate(dto.getDate());
		ret.setPersonId(dto.getPersonId());
		ret.setSeat(seat);
		ret.setDate(dto.getDate());
		ret.setCreationDate(LocalDate.now());
		ret.setId(1);

		when(reservationRepository.save(reservation)).thenReturn(ret);

	}

	@Test
	void testMakeReservation() {

		Reservation res = reservationService.makeReservation(dto);

		verify(reservationRepository).findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
				dto.getOfficeBuildingId());

		verify(seatRepository).findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
				dto.getSeatNumber(), dto.getFloorNumber());

		assertEquals(ret, res);

		// when(reservationRepository.save(reservation)).thenReturn(reservation);
		// when

		// Reservation reservation2 = reservationService.makeReservation(dto);
		// then
		// assertEquals(reservation, reservation2);

	}

	@Test
	void testDeleteReservation() {
		Reservation r = reservationService.makeReservation(dto);

		reservationService.deleteReservation(r.getId());

		verify(reservationRepository).findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
				dto.getOfficeBuildingId());

		verify(seatRepository).findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
				dto.getSeatNumber(), dto.getFloorNumber());

	}

	@Test
	void testGetReservationsAtGivenDate() {
		fail("Not yet implemented");
	}

	@Test
	void testGetReserervationsAtGivenTimeSpan() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateReservation() {
		fail("Not yet implemented");
	}

}
