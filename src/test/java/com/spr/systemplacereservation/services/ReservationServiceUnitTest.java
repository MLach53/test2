package com.spr.systemplacereservation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import com.spr.systemplacereservation.entity.dto.UpdateReservationDTO;
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
    private OfficeBuilding officeBuilding;
    private Seat seat;
    Reservation reservation;

    @BeforeEach
    void setUp() throws Exception {

	reservationService = new ReservationServiceImpl(reservationRepository, seatRepository, translator);

	// given
	officeBuilding = new OfficeBuilding();
	officeBuilding.setId(1);
	officeBuilding.setName("Gliwice");

	seat = new Seat();
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

	reservation = new Reservation();
	reservation.setDate(dto.getDate());
	reservation.setPersonId(dto.getPersonId());
	reservation.setSeat(seat);
	reservation.setDate(dto.getDate());

	ret = new Reservation();
	ret.setDate(dto.getDate());
	ret.setPersonId(dto.getPersonId());
	ret.setSeat(seat);
	ret.setDate(dto.getDate());
	ret.setCreationDate(LocalDate.now());
	ret.setId(1);

    }

    @Test
    void testMakeReservation() {

	// given

	when(reservationRepository.save(reservation)).thenReturn(ret);

	when(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
		dto.getSeatNumber(), dto.getFloorNumber())).thenReturn(Optional.of(seat));

	when(reservationRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
		dto.getOfficeBuildingId())).thenReturn(Optional.empty());

	// when

	Reservation res = reservationService.makeReservation(dto);

	// then

	verify(reservationRepository).findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
		dto.getOfficeBuildingId());

	verify(seatRepository).findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
		dto.getSeatNumber(), dto.getFloorNumber());

	assertEquals(ret, res);

    }

    @Test
    void testDeleteReservation() {

	// given

	when(reservationRepository.save(reservation)).thenReturn(ret);

	when(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
		dto.getSeatNumber(), dto.getFloorNumber())).thenReturn(Optional.of(seat));

	when(reservationRepository.findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
		dto.getOfficeBuildingId())).thenReturn(Optional.empty());

	Reservation r = reservationService.makeReservation(dto);

	when(reservationRepository.findById(r.getId())).thenReturn(Optional.of(reservation));

	// when

	reservationService.deleteReservation(r.getId());

	// then

	verify(reservationRepository).findFirstByDateAndPersonIdAndOfficeBuildingId(dto.getDate(), dto.getPersonId(),
		dto.getOfficeBuildingId());

	verify(seatRepository).findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto.getOfficeBuildingId(),
		dto.getSeatNumber(), dto.getFloorNumber());

    }

    @Test
    void testGetReservationsAtGivenDate() {

	// given

	LocalDate date = LocalDate.of(2022, 8, 18);
	List<Reservation> list = new ArrayList<>();
	list.add(ret);

	ReservationDTO dtoWithId = ReservationDTO.convertToDto(ret);

	when(reservationRepository.findByDate(date)).thenReturn(list);

	// when
	ReservationDTO temp = reservationService.getReservationsAtGivenDate(date).get(0);

	// then
	verify(reservationRepository).findByDate(date);
	assertEquals(temp, dtoWithId);

    }

    @Test
    void testGetReserervationsAtGivenTimeSpan() {
	// given

	LocalDate date1 = LocalDate.of(2022, 8, 19);
	LocalDate date2 = LocalDate.of(2022, 8, 20);

	List<Reservation> list = new ArrayList<>();
	list.add(ret);

	when(reservationRepository.findByDateBetween(date1, date2)).thenReturn(list);

	// when

	reservationService.getReserervationsAtGivenTimeSpan(date1, date2);

	// then

	verify(reservationRepository).findByDateBetween(date1, date2);

    }

    @Test
    void testUpdateReservation() {

	// given
	UpdateReservationDTO dto2 = UpdateReservationDTO.convertToDto(ret);
	System.out.println(dto2.getId());
	when(seatRepository.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto2.getOfficeBuildingId(),
		dto2.getSeatNumber(), dto2.getFloorNumber())).thenReturn(Optional.of(seat));

	when(reservationRepository.findById(dto2.getId())).thenReturn(Optional.of(ret));

	when(reservationRepository.save(ret)).thenReturn(ret);

	// when

	reservationService.updateReservation(dto2);

	// then

	verify(seatRepository).findByOfficeBuildingIdAndSeatNumberAndFloorNumber(dto2.getOfficeBuildingId(),
		dto2.getSeatNumber(), dto2.getFloorNumber());
	verify(reservationRepository).findById(dto2.getId());
	verify(reservationRepository).save(ret);
    }

}
