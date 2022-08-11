package com.spr.systemplacereservation.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.dto.ReservationDTO;
import com.spr.systemplacereservation.services.ReservationService;

public class DeleteReservationTest extends SystemplacereservationApplicationTests {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepositoryDAO dao;

    @Autowired
    private ReservationService reservationService;

    @Test
    void test() {
	reservationService.deleteReservation(Integer.valueOf(3));
	ReservationDTO reservationDTO = new ReservationDTO();

    }
}
