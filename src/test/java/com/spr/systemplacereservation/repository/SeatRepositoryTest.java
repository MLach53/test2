package com.spr.systemplacereservation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.model.OfficeBuilding;
import com.spr.systemplacereservation.model.Seat;

class SeatRepositoryTest extends SystemplacereservationApplicationTests {

    @Autowired
    SeatRepository repository;

    @Autowired
    OfficeBuildingRepository repo2;

    OfficeBuilding officeBuilding;

    Seat seat;

    @BeforeEach
    void setUp() {
	officeBuilding = new OfficeBuilding();
	officeBuilding.setName("Rybnik");
	seat = new Seat();
	seat.setFlooNumber(1);
	seat.setSeatNumber("AA");
	seat.setOfficeBuilding(officeBuilding);

    }

    @Transactional
    @Test
    void test() {
	// when
	int idOf = repo2.save(officeBuilding).getId();
	int idSe = repository.save(seat).getId();

	Optional<Seat> opt = repository.findById(idSe);
	Optional<OfficeBuilding> optOf = repo2.findById(idOf);

	// then
	assertEquals(true, opt.isPresent());
	assertEquals(true, optOf.isPresent());

	System.out.println(opt.get());
	System.out.println(optOf.get());

    }

}
