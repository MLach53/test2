package com.spr.systemplacereservation.bootstrap;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.bson.codecs.jsr310.LocalDateCodec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mongodb.BasicDBObject;
import com.spr.systemplacereservation.entity.OfficeBuilding;
import com.spr.systemplacereservation.entity.Reservation;
import com.spr.systemplacereservation.entity.Seat;
import com.spr.systemplacereservation.repository.OfficeBuildingRepository;
import com.spr.systemplacereservation.repository.ReservationRepository;
import com.spr.systemplacereservation.repository.SeatRepository;

@Component
public class Runner implements CommandLineRunner {

	@Autowired
	OfficeBuildingRepository repo;
	
	@Autowired
	SeatRepository repo2;
	
	@Autowired
	ReservationRepository repo3;
	@Autowired
	MongoTemplate temp;
	
	@Override
	public void run(String... args) throws Exception {
		
		repo.findAll().forEach(System.out::println);

		//repo2.findAll().forEach(System.out::println);

		//repo3.findAll().forEach(System.out::println);
		
		//System.out.println(repo.findByNamePun("Katowice"));
		
		//ObjectId  o = new ObjectId("6315e247e8027c34c551a8dc");
		
		//var a1 =  repo2.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(o, "AA", 0);
		
		//System.out.println(a1);
		
		//String seatNumber = "AA";
		
		//temp.findAll(Seat.class, "SEAT").forEach(System.out::println);;
		//temp.findAll(Reservation.class, "RESERVATION").forEach(System.out::println);;
		
		
		convertToMongoDBFormat(LocalDate.of(2022, 9, 6));
		
		Instant instant = Instant.parse("2022-09-06T00:00:00.000Z"); //Pass your date.

		Date timestamp = Date.from(instant);
		
		
		MatchOperation mop = Aggregation.match(Criteria.where("date").is(timestamp).and("personId").is(1));
		LookupOperation lookup = LookupOperation.newLookup().from("SEAT").localField("seat_id").foreignField("_id").as("seat");
		UnwindOperation uop = Aggregation.unwind("$seat");
		MatchOperation mopII = Aggregation.match(Criteria.where("seat.officebuilding_id").is(new ObjectId("631796f7867f7f2097c7e573")));
		
		Aggregation a = Aggregation.newAggregation(mop, lookup, uop, mopII);
		
		System.out.println(a);
		
		
		//List<Reservation> res = temp.aggregate(a, "RESERVATION", Reservation.class).getMappedResults();
		//System.out.println(res);
		
		System.out.println(repo2.findByOfficeBuildingIdAndSeatNumberAndFloorNumber(new ObjectId("631796f7867f7f2097c7e573"), "AA", 0));
		
		
		
		System.out.println("-----------------------------------");
		System.out.println(repo3.findFirstByDateAndPersonIdAndOfficeBuildingId(convertToMongoDBFormat(LocalDate.of(2022, 10, 7)), 3, new ObjectId("631796f7867f7f2097c7e573")));
		System.out.println("-----------------------------------");
		
		
		
		
		System.out.println("-----------------------------------");
		System.out.println(repo3.findByDate(convertToMongoDBFormat(LocalDate.of(2022, 10, 7))));
		System.out.println("-----------------------------------");
		
		
		
		
		
		System.out.println(repo3.findByDateBetween(convertToMongoDBFormat(LocalDate.of(2022, 9, 4)), convertToMongoDBFormat(LocalDate.of(2022, 9, 6))));
	}
	
	// ?
	public static Date convertToMongoDBFormat(LocalDate date) {
		String str = date.toString() + "T00:00:00.000Z";
		
		
		return Date.from(Instant.parse(str));
	}

}
