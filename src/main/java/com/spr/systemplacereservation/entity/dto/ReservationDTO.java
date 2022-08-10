package com.spr.systemplacereservation.entity.dto;

import java.sql.Date;

public class ReservationDTO {

	private int officeBuildingId;
	
	private int floorNumber;
	
	private String seatNumber;
	
	private Date date;
	
	private int personId;

	public int getOfficeBuildingId() {
		return officeBuildingId;
	}

	public void setOfficeBuildingId(int officeBuildingId) {
		this.officeBuildingId = officeBuildingId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	
	
}
