package com.spr.systemplacereservation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Seat;

class SeatRepositoryTest extends SystemplacereservationApplicationTests {

    @Autowired
    private SeatRepository repository;

    @Autowired
    private OfficeBuildingRepository repo2;

    private OfficeBuilding officeBuilding;

    private Seat seat;
    private int idOf;
    private int idSe;

    @BeforeEach
    void setUp() {
	officeBuilding = new OfficeBuilding();
	officeBuilding.setName("Rybnik");
	seat = new Seat();
	seat.setFlooNumber(1);
	seat.setSeatNumber("AA");
	seat.setOfficeBuilding(officeBuilding);

	idOf = repo2.save(officeBuilding).getId();
	idSe = repository.save(seat).getId();

    }

    @Test
    void test() {

	Optional<Seat> opt = repository.findById(idSe);
	Optional<OfficeBuilding> optOf = repo2.findById(idOf);

	// then
	assertEquals(true, opt.isPresent());
	assertEquals(true, optOf.isPresent());

    }

}
