package com.spr.systemplacereservation.repository;

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
	
	OfficeBuilding officeBuilding;
	
	Seat seat;
	
	@BeforeEach
	void setUp() {
		officeBuilding = new OfficeBuilding();
		officeBuilding.setName("Rybnik");
		seat = new Seat();
		seat.setFloor_number(1);
	}
	
	
	@Transactional
	@Test
	void test() {
		
		
		
	}

}
