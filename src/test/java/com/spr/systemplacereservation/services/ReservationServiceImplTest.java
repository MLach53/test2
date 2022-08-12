package com.spr.systemplacereservation.services;

import java.text.ParseException;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.exceptions.NotAvailableException;
import com.spr.systemplacereservation.exceptions.UserAlreadyReservedChairException;

/**
 * zmien pozniej by bylo niezalezne opd danych
 * 
 * @author MLach53
 *
 */
class ReservationServiceImplTest extends SystemplacereservationApplicationTests {

	@Autowired
	private ReservationService service;

	private ReservationDTO dto;

	@BeforeEach
	void setUp() throws ParseException {
		dto = new ReservationDTO();
		dto.setDate(LocalDate.of(2022, 8, 10));
		dto.setFloorNumber(2);
		dto.setOfficeBuildingId(1);
		dto.setSeatNumber("K");
		dto.setPersonId(5);
	}

	@Test
	void test() {

		service.makeReservation(dto);

		// given
		dto.setSeatNumber("L");

		// when
		var obj = Assertions.assertThrows(UserAlreadyReservedChairException.class, () -> {

			service.makeReservation(dto);
		});

		dto.setOfficeBuildingId(2);

		service.makeReservation(dto);

	}

	@Test
	void testNotForReservation() {

		dto.setOfficeBuildingId(1);
		dto.setFloorNumber(1);

		var obj = Assertions.assertThrows(NotAvailableException.class, () -> {

			service.makeReservation(dto);
		});

	}

	@Test
	void testNotExistent() {

		dto.setOfficeBuildingId(3);

		service.makeReservation(dto);

	}

}
