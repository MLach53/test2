package com.spr.systemplacereservation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.spr.systemplacereservation.SystemplacereservationApplicationTests;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.repository.SeatRepositoryDAO.SeatQuery;

class SeatRepositoryTest extends SystemplacereservationApplicationTests {

    @Autowired
    private SeatRepository repository;

    @Autowired
    private OfficeBuildingRepository repo2;

    @Autowired
    private SeatRepositoryDAO dao;

    private OfficeBuilding officeBuilding;

    private Seat seat;

    private int idOf;
    private int idSe;

    @BeforeEach
    void setUp() {
	officeBuilding = new OfficeBuilding();
	officeBuilding.setName("Rybnik");
	seat = new Seat();
	seat.setFloorNumber(1);
	seat.setSeatNumber("AA");
	seat.setOfficeBuilding(officeBuilding);
	seat.setReservationeligible(false);
	seat.setReDescription("bo tak");

    }

    @Test
    void test() {
	// when
	idOf = repo2.save(officeBuilding).getId();
	idSe = repository.save(seat).getId();

	Optional<Seat> opt = repository.findById(idSe);
	Optional<OfficeBuilding> optOf = repo2.findById(idOf);

	// then
	assertEquals(true, opt.isPresent());
	assertEquals(true, optOf.isPresent());

	// System.out.println(opt.get());

	SeatQuery query = new SeatQuery();
	query.setBuildingOffice(idOf);
	query.setFloorNumber(seat.getFloorNumber());
	query.setSeatNumber("AA");

	// System.out.println(dao.findSeatBy(query));

    }

    @Test
    void findByParamsTest() {
	idOf = repo2.save(officeBuilding).getId();
	idSe = repository.save(seat).getId();

	Optional<Seat> opt = repository.findById(idSe);
	Optional<OfficeBuilding> optOf = repo2.findById(idOf);

	// then
	assertEquals(true, opt.isPresent());
	assertEquals(true, optOf.isPresent());

	// System.out.println(opt.get());

	SeatQuery query = new SeatQuery();
	query.setBuildingOffice(idOf);
	query.setFloorNumber(seat.getFloorNumber());
	query.setSeatNumber("AA");

	// System.out.println("query test: " + dao.findSeatBy(query));
    }

}
