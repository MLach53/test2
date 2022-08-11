package com.spr.systemplacereservation.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spr.systemplacereservation.entity.Seat;

@Repository
public class SeatRepositoryDAO {

    @Autowired
    private EntityManager manager;

    @Autowired
    private SeatRepository seatRepository;

    public Seat findSeatBy(SeatQuery query) {

	CriteriaBuilder builder = manager.getCriteriaBuilder();
	CriteriaQuery<Seat> criteria = builder.createQuery(Seat.class);

	criteria.distinct(true);

	Root<Seat> seat = criteria.from(Seat.class);

	seat.fetch("officeBuilding", JoinType.LEFT);

	Predicate officePredicate = builder.equal(seat.get("officeBuilding").get("id"), query.getBuildingOffice());

	Predicate seatPredicate = builder.equal(seat.get("seatNumber"), query.getSeatNumber());

	Predicate floorPredicate = builder.equal(seat.get("floorNumber"), query.getFloorNumber());

	criteria.where(officePredicate, seatPredicate, floorPredicate);

	TypedQuery<Seat> resultQuery = manager.createQuery(criteria);

	return resultQuery.getSingleResult();
    }

    public static class SeatQuery {

	private int buildingOffice;
	private int floorNumber;
	private String seatNumber;

	public int getBuildingOffice() {
	    return buildingOffice;
	}

	public void setBuildingOffice(int buildingOffice) {
	    this.buildingOffice = buildingOffice;
	}

	public int getFloorNumber() {
	    return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
	    this.floorNumber = floorNumber;
	}

	public String getSeatNumber() {
	    return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
	    this.seatNumber = seatNumber;
	}

    }

}
