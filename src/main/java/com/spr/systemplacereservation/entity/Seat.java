package com.spr.systemplacereservation.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "SEAT")
public class Seat {

	@Id
	private String id;

	@DBRef
	@Field(name = "officebuildign_id")
	private OfficeBuilding officeBuilding;

	@Field(name = "floor_number")
	private Integer floorNumber;

	@Field(name = "seat_number")
	private String seatNumber;

	@Field(name = "reservationeligible")
	private boolean reservationEligible;

	@Field(name = "description")
	private String reDescription;

	public OfficeBuilding getOfficeBuilding() {
		return officeBuilding;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOfficeBuilding(OfficeBuilding officeBuilding) {
		this.officeBuilding = officeBuilding;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer flooNumber) {
		this.floorNumber = flooNumber;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public boolean getReservationeligible() {
		return reservationEligible;
	}

	public void setReservationeligible(boolean reservationeligible) {
		this.reservationEligible = reservationeligible;
	}

	public String getReDescription() {
		return reDescription;
	}

	public void setReDescription(String reDescription) {
		this.reDescription = reDescription;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", officeBuilding=" + officeBuilding + ", flooNumber=" + floorNumber + ", seatNumber="
				+ seatNumber + ", reservationeligible=" + reservationEligible + ", reDescription=" + reDescription
				+ "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorNumber, id, officeBuilding, reDescription, reservationEligible, seatNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		return Objects.equals(floorNumber, other.floorNumber) && Objects.equals(id, other.id)
				&& Objects.equals(officeBuilding, other.officeBuilding)
				&& Objects.equals(reDescription, other.reDescription)
				&& Objects.equals(reservationEligible, other.reservationEligible)
				&& Objects.equals(seatNumber, other.seatNumber);
	}

}
