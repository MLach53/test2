package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceUnitTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatRepository seatRepository;

    @InjectMocks
    ReservationService reservationService = new ReservationServiceImpl(reservationRepository, seatRepository, null);

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void testMakeReservation() {
	// given
	OfficeBuilding officeBuilding = new OfficeBuilding();
	officeBuilding.setId(1);
	officeBuilding.setName("Gliwice");
	Seat seat = new Seat();
	officeBuilding.getSeats().add(seat);
	seat.setFloorNumber(1);
	seat.setId(1);
	seat.setOfficeBuilding(officeBuilding);
	seat.setReservationeligible(true);
	seat.setSeatNumber("A");

	when(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(null, null, null))
		.thenReturn(Optional.of(new Seat()));
	ReservationDTO dto = new ReservationDTO(null, null, null, null, null, null, null);

	Reservation reservation = new Reservation();
	reservation.setDate(dto.getDate());
	reservation.setPersonId(dto.getPersonId());
	reservation.setSeat(seat);
	reservation.setDate(dto.getDate());
	when(reservationRepository.save(reservation)).thenReturn(reservation);
	// when
	Reservation reservation2 = reservationService.makeReservation(dto);
	// then
	assertEquals(reservation, reservation2);

    }

    @Test
    void testDeleteReservation() {
	// fail("Not yet implemented");
    }

    @Test
    void testGetReservationsAtGivenDate() {
	// fail("Not yet implemented");
    }

    @Test
    void testGetReserervationsAtGivenTimeSpan() {
	// fail("Not yet implemented");
    }

    @Test
    void testUpdateReservation() {
	// fail("Not yet implemented");
    }

}
